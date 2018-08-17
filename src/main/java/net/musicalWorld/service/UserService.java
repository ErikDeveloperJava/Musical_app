package net.musicalWorld.service;

import net.musicalWorld.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void save(User user, MultipartFile image);

    boolean existsByUsername(String username);
}