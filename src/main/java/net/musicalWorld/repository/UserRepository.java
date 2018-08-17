package net.musicalWorld.repository;

import net.musicalWorld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    @Query("select size(u.musicList) from User u where u.id=:userId")
    int bookmarksCountByUserId(@Param("userId")int userId);

    boolean existsByUsername(String username);
}
