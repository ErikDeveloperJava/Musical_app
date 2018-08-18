package net.musicalWorld.controller.admin;

import net.musicalWorld.page.Pages;
import net.musicalWorld.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String main(Model model){
        model.addAttribute("users",adminService.getUsers());
        return ADMIN_INDEX;
    }
}
