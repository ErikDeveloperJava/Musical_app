package net.musicalWorld.repository;

import net.musicalWorld.model.Music;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Integer> {

    @Query("select m from Music m order by size(m.users) desc ")
    List<Music> findAllPopulars(Pageable pageable);

    List<Music> findTop10ByYear(int year);

    @Query("select m from Music m join m.albums a on a.id = :albumId")
    List<Music> findAllByAlbumId(@Param("albumId") int albumId);
}
