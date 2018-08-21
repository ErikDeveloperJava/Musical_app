package net.musicalWorld.service.impl;

import net.musicalWorld.model.Music;
import net.musicalWorld.model.User;
import net.musicalWorld.page.AllMusic;
import net.musicalWorld.repository.MusicRepository;
import net.musicalWorld.repository.NewsRepository;
import net.musicalWorld.repository.UserRepository;
import net.musicalWorld.service.BookmarkService;
import net.musicalWorld.service.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public int countByUserId(int userId) {
        return userRepository.bookmarksCountByUserId(userId);
    }

    @Override
    public List<Music> getAllByUserId(int userId) {
        return musicRepository.findAllByUsers_id(userId);
    }

    @Transactional
    public boolean addOrDelete(int musicId, User user) {
        boolean flag;
        List<Music> musicList = musicRepository.findAllByUsers_id(user.getId());
        Music music = musicRepository.findById(musicId).get();
        if(musicList.contains(music)){
            flag = false;
            musicList.remove(music);
        }else {
            musicList.add(music);
            flag = true;
        }
        user.setMusicList(musicList);
        userRepository.save(user);
        return flag;
    }

    @Override
    public AllMusic getAllBookmark(int userId, Pageable pageable) {
        return AllMusic.builder()
                .musicList(musicRepository.findAllByUsers_idAndLimit(userId,
                        PageRequest.of(0,10,Sort.Direction.DESC,"id")))
                .newsList(newsRepository.findAll(
                        PageRequest.of(0,2,Sort.Direction.DESC,"createdDate")).getContent())
                .build();
    }
}
