package net.musicalWorld.controller;

import net.musicalWorld.model.User;
import net.musicalWorld.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/bookmarks/count")
    public @ResponseBody
    int bookmarks(@RequestAttribute("user")User user){
        return bookmarkService.countByUserId(user.getId());
    }
}