package net.musicalWorld.controller;

import net.musicalWorld.form.UserRequestForm;
import net.musicalWorld.model.User;
import net.musicalWorld.model.enums.UserRole;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.UserService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginRegisterController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return LOGIN;
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("form",new UserRequestForm());
        return REGISTER;
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("form")@Valid UserRequestForm form, BindingResult result){
        if(result.hasErrors()){
            return REGISTER;
        }else if(userService.existsByUsername(form.getUsername())){
            result.addError(new FieldError("form","username",""));
            return REGISTER;
        }else if(form.getImage().isEmpty() || !fileUtil.isValidImgFormat(form.getImage().getContentType())){
            result.addError(new FieldError("form","image",""));
            return REGISTER;
        }else {
            User user = User.builder()
                    .name(form.getName())
                    .surname(form.getSurname())
                    .username(form.getUsername())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .role(UserRole.USER)
                    .imgUrl("")
                    .build();
            userService.save(user,form.getImage());
            return "redirect:/login";
        }
    }
}
