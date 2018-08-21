package net.musicalWorld.service.impl;

import net.musicalWorld.model.Album;
import net.musicalWorld.model.Music;
import net.musicalWorld.model.Musician;
import net.musicalWorld.page.MusicianDetail;
import net.musicalWorld.repository.AlbumRepository;
import net.musicalWorld.repository.MusicRepository;
import net.musicalWorld.repository.MusicianRepository;
import net.musicalWorld.service.MusicianService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MusicianServiceImpl implements MusicianService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicianRepository musicianRepository;

    @Transactional(rollbackFor = Exception.class)
    public void add(Musician musician, MultipartFile image) {
        musician = musicianRepository.save(musician);
        String imgUrl = System.currentTimeMillis() + image.getOriginalFilename();
        musician.setImgUrl(musician.getId() + "/" + imgUrl);
        try {
            fileUtil.saveImage("musicians\\" + musician.getId(),imgUrl,image.getBytes());
            LOGGER.debug("musician saved");
        }catch (Exception e){
            fileUtil.deleteImg("musicians\\" + musician.getId());
        }
    }

    @Override
    public List<Musician> getAll(Pageable pageable) {
        return musicianRepository.findAll(PageRequest.of(pageable.getPageNumber(),6)).getContent();
    }

    @Override
    public List<Musician> getAll() {
        return musicianRepository.findAll();
    }

    @Override
    public int count() {
        return (int) musicianRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        List<Album> albums = albumRepository.findAllByMusicianId(id);
        for (Album album : albums) {
            List<Music> musicList = musicRepository.findAllByAlbumId(album.getId());
            for (Music music : musicList) {
                fileUtil.deleteMusic(music.getId() + "");
            }
            fileUtil.deleteImg("albums\\" + album.getId());
        }
        musicianRepository.deleteById(id);
        fileUtil.deleteImg("musicians\\" + id);
        LOGGER.debug("musician deleted");
    }

    @Override
    public MusicianDetail getDetailById(int id) {
        List<Album> albums = albumRepository.findAllByMusicianId(id);
        List<Music> musicList = new ArrayList<>();
        for (Album album : albums) {
            Music music = musicRepository.findFirstByAlbums_Id(album.getId());
            if(music != null){
                musicList.add(music);
            }
        }
        return MusicianDetail.builder()
                .musician(musicianRepository.findById(id).get())
                .albums(albums)
                .musicList(musicList)
                .build();
    }

    @Override
    public boolean existsById(int id) {
        return musicianRepository.existsById(id);
    }

    @Override
    public int countByNameContains(String name) {
        return musicianRepository.countByNameContains(name);
    }

    @Override
    public List<Musician> getAllByNameContains(String name, Pageable pageable) {
        return musicianRepository.findAllByNameContains(name,PageRequest.of(pageable.getPageNumber(),6));
    }
}