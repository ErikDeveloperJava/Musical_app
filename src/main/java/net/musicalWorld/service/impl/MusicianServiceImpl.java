package net.musicalWorld.service.impl;

import net.musicalWorld.model.Musician;
import net.musicalWorld.repository.MusicianRepository;
import net.musicalWorld.service.MusicianService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class MusicianServiceImpl implements MusicianService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

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
}
