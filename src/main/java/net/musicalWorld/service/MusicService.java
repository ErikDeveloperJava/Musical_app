package net.musicalWorld.service;

import net.musicalWorld.form.MusicRequestForm;
import net.musicalWorld.model.Music;
import net.musicalWorld.page.AllMusic;
import org.springframework.data.domain.Pageable;

public interface MusicService {

    Music add(MusicRequestForm form);

    AllMusic getAllMusic(Pageable pageable, char font);

    int count();

    int countByNameStartingWith(char font);

    int countByNameLike(String name);

    AllMusic getAllByNameContains(String name,Pageable pageable);

    void delete(int id);

    AllMusic getAllByCategoryId(int catId,Pageable pageable);

    int countByCategoryId(int catId);
}
