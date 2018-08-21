package net.musicalWorld.service;

import net.musicalWorld.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    void add(News news, MultipartFile image);

    List<News> getAll(Pageable pageable);

    int count();

    void deleteById(int id);

    News getById(int id);

    boolean existsById(int id);

    List<News> getTop5AndUnlessId(int id);
}
