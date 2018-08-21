package net.musicalWorld.repository;

import net.musicalWorld.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Integer> {

    List<News> findTop4ByOrderByCreatedDateDesc();

    @Query("select n from News n  where n.id != :id")
    List<News> findTop5AndUnlessId(@Param("id") int id, Pageable pageable);
}
