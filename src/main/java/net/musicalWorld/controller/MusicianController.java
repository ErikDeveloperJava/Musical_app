package net.musicalWorld.controller;

import net.musicalWorld.form.MusicianRequestForm;
import net.musicalWorld.model.Musician;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.MusicianService;
import net.musicalWorld.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MusicianController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/admin/musician/add")
    public String addGet(Model model) {
        model.addAttribute("form", new MusicianRequestForm());
        return MUSICIAN_ADD;
    }

    @PostMapping("/admin/musician/add")
    public String addPost(@ModelAttribute("form") @Valid MusicianRequestForm form, BindingResult result) {
        LOGGER.debug("form : {}",form);
        if (result.hasErrors()) {
            return MUSICIAN_ADD;
        } else if (form.getImage().isEmpty() || !fileUtil.isValidImgFormat(form.getImage().getContentType())) {
            result.addError(new FieldError("form","image",""));
            return MUSICIAN_ADD;
        }else {
            Musician musician = Musician.builder()
                    .name(form.getName())
                    .surname(form.getSurname())
                    .birthDate(form.getBirthDate())
                    .biography(form.getBiography())
                    .imgUrl("")
                    .build();
            musicianService.add(musician,form.getImage());
        }
        return "redirect:/musicians";
    }
}
