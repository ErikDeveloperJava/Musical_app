package net.musicalWorld.controller;

import net.musicalWorld.model.Music;
import net.musicalWorld.model.User;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.BookmarkService;
import net.musicalWorld.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class BookmarkController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int PAGE_SIZE = 10;

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/bookmarks/count")
    public @ResponseBody
    int bookmarksCount(@RequestAttribute("user")User user){
        return bookmarkService.countByUserId(user.getId());
    }

    @PostMapping("/bookmarks")
    public @ResponseBody
    List<Music> bookmarks(@RequestAttribute("user")User user){
        return bookmarkService.getAllByUserId(user.getId());
    }

    @PostMapping("/bookmark/add")
    public @ResponseBody
    boolean add(@RequestParam("musicId")int musicId,
                @RequestAttribute("user") User user){
        LOGGER.debug("musicId : {}",musicId);
        boolean result = bookmarkService.addOrDelete(musicId, user);
        user.setMusicList(null);
        return result;
    }

    @GetMapping("/bookmarks")
    public String userBookmarks(@RequestAttribute("user")User user, Model model,
                                Pageable pageable){
        int count = bookmarkService.countByUserId(user.getId());
        int length = PageableUtil.getLength(count,PAGE_SIZE);
        pageable = PageableUtil.getChecked(pageable,length);
        model.addAttribute("bookmark",bookmarkService.getAllBookmark(user.getId(),pageable));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length);
        return BOOKMARK;
    }
}