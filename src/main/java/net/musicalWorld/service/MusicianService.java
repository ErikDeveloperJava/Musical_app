package net.musicalWorld.service;

import net.musicalWorld.model.Musician;
import org.springframework.web.multipart.MultipartFile;

public interface MusicianService {

    void add(Musician musician, MultipartFile image);
}
