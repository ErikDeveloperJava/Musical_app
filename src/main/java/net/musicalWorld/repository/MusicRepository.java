package net.musicalWorld.repository;

import net.musicalWorld.model.Music;
import net.musicalWorld.page.AllMusic;
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

    Music findFirstByAlbums_Id(int albumId);

    List<Music> findAllByUsers_id(int userId);

    int countByUsers_Id(int userId);

    @Query("select m from Music m join m.users u on u.id = :userId")
    List<Music> findAllByUsers_idAndLimit(@Param("userId") int userId,Pageable pageable);

    Music findByIdAndUsers_Id(int id,int userId);

    List<Music> findAllByNameStartingWith(String name,Pageable pageable);

    List<Music> findAllByNameContains(String name,Pageable pageable);

    int countByNameStartingWith(String name);

    int countByNameContains(String name);

    List<Music> findAllByCategories_Id(int catId,Pageable pageable);

    int countByCategories_Id(int catId);
}
