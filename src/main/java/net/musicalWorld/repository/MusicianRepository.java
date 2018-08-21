package net.musicalWorld.repository;

import net.musicalWorld.model.Musician;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicianRepository extends JpaRepository<Musician,Integer> {

    @Query("select m from Musician m order by size(m.albums) desc")
    List<Musician> findAllByOrderByAlbumsCount(Pageable pageable);

    @Query("select m from Musician m where :albumId member m.albums")
    Musician findByAlbumId(@Param("albumId") int albumId);

    List<Musician> findAllByNameContains(String name,Pageable pageable);

    int countByNameContains(String name);
}
