package net.musicalWorld.service.impl;

import net.musicalWorld.form.MusicRequestForm;
import net.musicalWorld.model.Music;
import net.musicalWorld.model.News;
import net.musicalWorld.page.AllMusic;
import net.musicalWorld.repository.AlbumRepository;
import net.musicalWorld.repository.CategoryRepository;
import net.musicalWorld.repository.MusicRepository;
import net.musicalWorld.repository.NewsRepository;
import net.musicalWorld.service.MusicService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public Music add(MusicRequestForm form) {
        Music music = Music.builder()
                .name(form.getName())
                .year(Integer.parseInt(form.getYear()))
                .mp3("")
                .build();
        musicRepository.save(music);
        music.setCategories(categoryRepository.findAllById(form.getCategories()));
        music.setAlbums(albumRepository.findAllById(form.getAlbums()));
        String musicUrl = System.currentTimeMillis() + form.getMusic().getOriginalFilename();
        music.setMp3(music.getId() + "/" + musicUrl);
        try {
            fileUtil.saveMusic(music.getId() + "", musicUrl, form.getMusic().getBytes());
            musicRepository.save(music);
            LOGGER.debug("music saved");
            return music;
        } catch (Exception e) {
            fileUtil.deleteMusic(music.getId() + "");
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public AllMusic getAllMusic(Pageable pageable, char font) {
        List<Music> musicList;
        List<News> newsList = new ArrayList<>();
        if (font != '0') {
            musicList = musicRepository.findAllByNameStartingWith(String.valueOf(font),PageRequest.of(0,10,Sort.Direction.DESC,"id"));
        } else {
            musicList = musicRepository.findAll(PageRequest.of(0,10,Sort.Direction.DESC,"id")).getContent();
            newsList = newsRepository.findAll(PageRequest.of(0,2,Sort.Direction.DESC,"createdDate")).getContent();
        }
        return AllMusic.builder()
                .musicList(musicList)
                .newsList(newsList)
                .build();
    }

    @Transactional(readOnly = true)
    public int count() {
        return (int) musicRepository.count();
    }

    @Transactional(readOnly = true)
    public int countByNameStartingWith(char font) {
        return musicRepository.countByNameStartingWith(String.valueOf(font));
    }

    @Transactional(readOnly = true)
    public int countByNameLike(String name) {
        return musicRepository.countByNameContains(name);
    }

    @Override
    public AllMusic getAllByNameContains(String name,Pageable pageable) {
        return AllMusic.builder()
                .musicList(musicRepository.findAllByNameContains(name,
                        PageRequest.of(pageable.getPageNumber(),10,Sort.Direction.DESC,"id")))
                .newsList(newsRepository.findAll(PageRequest.of(0,2,Sort.Direction.DESC,"createdDate")).getContent())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        musicRepository.deleteById(id);
        fileUtil.deleteMusic("" + id);
        LOGGER.debug("music deleted");
    }

    @Override
    public AllMusic getAllByCategoryId(int catId, Pageable pageable) {
        return AllMusic.builder()
                .newsList(newsRepository.findAll(PageRequest.of(pageable.getPageNumber(),2,Sort.Direction.DESC,"createdDate")).getContent())
                .musicList(musicRepository.findAllByCategories_Id(catId,PageRequest.of(0,10,Sort.Direction.DESC,"id")))
                .build();
    }

    @Override
    public int countByCategoryId(int catId) {
        return musicRepository.countByCategories_Id(catId);
    }
}
