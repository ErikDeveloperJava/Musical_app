package net.musicalWorld.controller.admin;

import net.musicalWorld.page.Pages;
import net.musicalWorld.service.AdminService;
import net.musicalWorld.service.MainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AdminService adminService;

    @Autowired
    private MainService mainService;

    @GetMapping
    public String main(Model model){
        model.addAttribute("users",adminService.getUsers());
        return ADMIN_INDEX;
    }

    @PostMapping("/home/music/change")
    public @ResponseBody
    boolean changedMusic(@RequestParam("musicId")int musicId){
        LOGGER.debug("musicId : {}",musicId);
        mainService.changeMusic(musicId);
        return true;
    }

    @GetMapping("/main/change")
    public String change(Model model){
        model.addAttribute("home",mainService.getHome());
        return CHANGE_MAIN_PAGE;
    }
}