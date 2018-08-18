package net.musicalWorld.controller.admin;

import net.musicalWorld.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @PostMapping("/admin/user/delete")
    public @ResponseBody
    boolean delete(@RequestParam("userId")int userId){
        LOGGER.debug("userId : {}",userId);
        userService.deleteById(userId);
        return true;
    }
}
