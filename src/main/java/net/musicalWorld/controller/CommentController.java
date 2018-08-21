package net.musicalWorld.controller;

import net.musicalWorld.model.News;
import net.musicalWorld.model.User;
import net.musicalWorld.page.CommentDetail;
import net.musicalWorld.service.CommentService;
import net.musicalWorld.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/comments")
    public @ResponseBody
    List<CommentDetail> comments(@RequestParam("newsId")int newsId){
        LOGGER.debug("newsId : {}",newsId);
        return commentService.getAllByNewsId(newsId);
    }
    @PostMapping("/comment/add")
    public @ResponseBody
    net.musicalWorld.model.Comment add(@RequestParam("newsId")int newsId, @RequestParam("comment")String commentText,
                                       @RequestAttribute("user")User user, @RequestParam(value = "parentId",required = false,defaultValue = "0")int parentId){
        LOGGER.debug("newsId : {}, comment : {}",newsId,commentText);
        if(!newsService.existsById(newsId) || commentText.length() == 0){
            return null;
        }else {
            net.musicalWorld.model.Comment comment = net.musicalWorld.model.Comment.builder()
                    .comment(commentText)
                    .news(News.builder().id(newsId).build())
                    .user(user)
                    .sendDate(new Date())
                    .parent(parentId == 0 ? null : net.musicalWorld.model.Comment.builder().id(parentId).build())
                    .build();
            commentService.add(comment);
            return comment;
        }
    }
}
