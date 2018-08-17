package net.musicalWorld.service.impl;

import net.musicalWorld.model.User;
import net.musicalWorld.repository.UserRepository;
import net.musicalWorld.service.UserService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUtil fileUtil;

    @Transactional(rollbackFor = Exception.class)
    public void save(User user, MultipartFile image) {
        userRepository.save(user);
        String imgName = System.currentTimeMillis() + user.getUsername() + ".jpg";
        try {
            fileUtil.saveImage("users" + "\\" + user.getId(),imgName,image.getBytes());
            user.setImgUrl(user.getId() + "/" + imgName);
            LOGGER.debug("user saved");
        }catch (Exception e){
            fileUtil.deleteImg("users" + "\\" + user.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
