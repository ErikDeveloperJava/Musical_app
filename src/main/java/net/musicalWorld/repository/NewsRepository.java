package net.musicalWorld.repository;

import net.musicalWorld.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Integer> {

    List<News> findTop4ByOrderByCreatedDateDesc();
}
