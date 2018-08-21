package net.musicalWorld.repository;

import net.musicalWorld.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByParentIsNullAndNewsId(int newsId);

    List<Comment> findAllByParentId(int parentId);

    int countByParentId(int parentId);
}
