package net.musicalWorld.service.impl;

import net.musicalWorld.repository.UserRepository;
import net.musicalWorld.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public int countByUserId(int userId) {
        return userRepository.bookmarksCountByUserId(userId);
    }
}
