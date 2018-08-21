package net.musicalWorld.service;

import net.musicalWorld.model.Musician;
import net.musicalWorld.page.MusicianDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicianService {

    void add(Musician musician, MultipartFile image);

    List<Musician> getAll(Pageable pageable);

    List<Musician> getAll();

    int count();

    void deleteById(int id);

    MusicianDetail getDetailById(int id);

    boolean existsById(int id);

    int countByNameContains(String name);

    List<Musician> getAllByNameContains(String name,Pageable pageable);
}
