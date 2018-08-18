package net.musicalWorld.service.impl;

import net.musicalWorld.model.Album;
import net.musicalWorld.model.Music;
import net.musicalWorld.repository.AlbumRepository;
import net.musicalWorld.repository.MusicRepository;
import net.musicalWorld.service.AlbumService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private FileUtil fileUtil;

    @Transactional(rollbackFor = Exception.class)
    public void add(Album album, MultipartFile image) {
        album = albumRepository.save(album);
        String imgName = System.currentTimeMillis() + image.getOriginalFilename();
        album.setImgUrl(album.getId() + "/" + imgName);
        try {
            fileUtil.saveImage("albums\\" + album.getId(),imgName,image.getBytes());
            LOGGER.debug("album saved");
        }catch (Exception e){
            fileUtil.deleteImg("albums\\" + album.getId());
        }
    }

    @Override
    public List<Album> getAll(Pageable pageable) {
        return albumRepository.findAll(PageRequest.of(pageable.getPageNumber(),6)).getContent();
    }

    @Override
    public int count() {
        return (int) albumRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        List<Music> musicList = musicRepository.findAllByAlbumId(id);
        for (Music music : musicList) {
            fileUtil.deleteMusic(music.getId() + "");
        }
        albumRepository.deleteById(id);
        fileUtil.deleteImg("albums\\" + id);
        LOGGER.debug("album deleted");
    }
}
