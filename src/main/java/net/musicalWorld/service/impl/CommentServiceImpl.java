package net.musicalWorld.service.impl;

import net.musicalWorld.page.CommentDetail;
import net.musicalWorld.repository.CommentRepository;
import net.musicalWorld.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentDetail> getAllByNewsId(int newsId) {
        List<CommentDetail> commentDetailPages = new ArrayList<>();
        List<net.musicalWorld.model.Comment> comments = commentRepository.findAllByParentIsNullAndNewsId(newsId);
        for (net.musicalWorld.model.Comment comment : comments) {
            CommentDetail commentDetailPage = CommentDetail.builder()
                    .comment(comment)
                    .build();
            getChildrenList(commentDetailPage);
            commentDetailPages.add(commentDetailPage);
        }
        return commentDetailPages;
    }

    private void getChildrenList(CommentDetail commentDetailPage){
        List<net.musicalWorld.model.Comment> childrenList = commentRepository.findAllByParentId(commentDetailPage.getComment().getId());
        if(!childrenList.isEmpty()){
            List<CommentDetail> commentDetails = new ArrayList<>();
            for (net.musicalWorld.model.Comment comment : childrenList) {
                CommentDetail page = CommentDetail.builder()
                        .comment(comment)
                        .build();
                getChildrenList(page);
                commentDetails.add(page);
            }
            commentDetailPage.setChildrenList(commentDetails);
        }
    }

    @Transactional
    public void add(net.musicalWorld.model.Comment comment) {
        commentRepository.save(comment);
        LOGGER.debug("comment saved");
    }

}
