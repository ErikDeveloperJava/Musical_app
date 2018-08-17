package net.musicalWorld.controller;

import net.musicalWorld.model.Home;
import net.musicalWorld.model.User;
import net.musicalWorld.model.enums.UserRole;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.MainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MainService mainService;

    @GetMapping("")
    public String main(@RequestAttribute("user")User user, Model model){
        if(user.getRole().equals(UserRole.ADMIN)){
            LOGGER.debug("user role 'ADMIN' redirect to '/admin' url");
            return "redirect:/admin";
        }else {
            model.addAttribute("main",mainService.getMainData());
            return INDEX;
        }
    }

    @PostMapping("/home")
    public @ResponseBody
    Home home(){
        return mainService.getHome();
    }
}
