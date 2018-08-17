package net.musicalWorld.service.impl;

import net.musicalWorld.model.Home;
import net.musicalWorld.page.Main;
import net.musicalWorld.repository.*;
import net.musicalWorld.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MusicianRepository musicianRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private HomeRepository homeRepository;

    @Override
    public Main getMainData() {
        return Main.builder()
                .newsList(newsRepository.findTop4ByOrderByCreatedDateDesc())
                .albumList(albumRepository.findAllByOrderByMusicCount(PageRequest.of(0,4)))
                .musicianList(musicianRepository.findAllByOrderByAlbumsCount(PageRequest.of(0,4)))
                .newMusicList(musicRepository.findTop10ByYear(2018))
                .popularsMusicList(musicRepository.findAllPopulars(PageRequest.of(0,10)))
                .music(homeRepository.findAll().get(0).getMusic())
                .build();
    }

    @Override
    public Home getHome() {
        return homeRepository.findAll().get(0);
    }
}
