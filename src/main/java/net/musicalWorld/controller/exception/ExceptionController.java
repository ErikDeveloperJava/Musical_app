package net.musicalWorld.controller.exception;

import net.musicalWorld.page.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController implements Pages {

    @GetMapping("/error")
    public String error500(){
        return ERROR_500;
    }
}
