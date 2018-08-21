package net.musicalWorld.repository;

import net.musicalWorld.model.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Integer> {

    @Query("select a from Album a order by size(a.musicList) desc ")
    List<Album> findAllByOrderByMusicCount(Pageable pageable);

    @Query("select a from Album a where a.musician.id = :musicianId")
    List<Album> findAllByMusicianId(@Param("musicianId") int musicianId);

    List<Album> findTop4ByMusicianIdAndIdNotIn(int musicianId,int id);

    List<Album> findAllByNameContains(String name,Pageable pageable);

    int countByNameContains(String name);
}
