package net.musicalWorld.repository;

import net.musicalWorld.model.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Integer> {

    @Query("select a from Album a order by size(a.musicList) desc ")
    List<Album> findAllByOrderByMusicCount(Pageable pageable);
}
