package net.musicalWorld.service;

import net.musicalWorld.page.CommentDetail;

import java.util.List;

public interface CommentService {

    List<CommentDetail> getAllByNewsId(int newsId);

    void add(net.musicalWorld.model.Comment comment);
}
