package net.musicalWorld.service;

import net.musicalWorld.model.Music;
import net.musicalWorld.model.User;
import net.musicalWorld.page.AllMusic;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookmarkService {

    int countByUserId(int userId);

    List<Music> getAllByUserId(int userId);

    boolean addOrDelete(int musicId, User user);

    AllMusic getAllBookmark(int userId, Pageable pageable);
}
