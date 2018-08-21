package net.musicalWorld.service;

import net.musicalWorld.model.Album;
import net.musicalWorld.page.AlbumDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumService {

    void add(Album album, MultipartFile image);

    List<Album> getAll(Pageable pageable);

    int count();

    void deleteById(int id);

    AlbumDetail getDetailById(int id);

    boolean existsById(int id);

    List<Album> getAll();

    List<Album> getAllByNameContains(String name,Pageable pageable);

    int countByNameContains(String name);
}
