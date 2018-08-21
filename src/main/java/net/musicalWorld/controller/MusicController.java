package net.musicalWorld.controller;

import net.musicalWorld.form.MusicRequestForm;
import net.musicalWorld.form.MusicResponseForm;
import net.musicalWorld.model.Category;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.CategoryService;
import net.musicalWorld.service.MusicService;
import net.musicalWorld.util.FileUtil;
import net.musicalWorld.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MusicController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final int PAGE_SIZE = 10;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private MusicService musicService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/music/add")
    public String addGet() {
        return MUSIC_ADD;
    }

    @PostMapping("/admin/music/add")
    public @ResponseBody
    MusicResponseForm addPost(@Valid MusicRequestForm form, BindingResult result) {
        LOGGER.debug("form : {}", form);
        if (result.hasErrors()) {
            return MusicResponseForm.builder()
                    .isNameError(true)
                    .build();
        } else if (getCheckedYear(form.getYear()) == -1) {
            return MusicResponseForm.builder()
                    .isYearError(true)
                    .build();
        } else if (form.getMusic().isEmpty() || !fileUtil.isValidMusicFormat(form.getMusic().getContentType())) {
            return MusicResponseForm.builder()
                    .isMusicError(true)
                    .build();
        } else {
            return MusicResponseForm.builder()
                    .isSuccess(true)
                    .music(musicService.add(form))
                    .build();
        }
    }

    private int getCheckedYear(String strYear) {
        int year;
        try {
            year = Integer.parseInt(strYear);
            if (year < 2005 || year > 2019) {
                year = -1;
            }
        } catch (NumberFormatException e) {
            year = -1;
        }
        return year;
    }

    @GetMapping("/music")
    public String allMusic(Pageable pageable, Model model,
                           @RequestParam(value = "font", required = false, defaultValue = "-") char font,
                           @RequestParam(value = "nimda", required = false, defaultValue = "NONE") String admin) {
        boolean isJs = false;
        int length;
        if (font == '-') {
            length = PageableUtil.getLength(musicService.count(),PAGE_SIZE);
            pageable = PageableUtil.getChecked(pageable, length);
            model.addAttribute("allMusic", musicService.getAllMusic(pageable, '0'));
            if (!admin.equals("NONE")) {
                isJs = true;
            }
        } else {
            length = PageableUtil.getLength(musicService.countByNameStartingWith(font),PAGE_SIZE);
            pageable = PageableUtil.getChecked(pageable, length);
            model.addAttribute("allMusic", musicService.getAllMusic(pageable, font));
            model.addAttribute("font", font);
            isJs = true;
        }
        model.addAttribute("pageNumber", pageable.getPageNumber());
        model.addAttribute("length", length);
        return isJs ? ALL_MUSIC_JS : ALL_MUSIC;
    }

    @PostMapping("/admin/music/delete")
    public @ResponseBody
    boolean delete(@RequestParam("musicId") int musicId) {
        LOGGER.debug("musicId : {}", musicId);
        musicService.delete(musicId);
        return true;
    }

    @GetMapping("/music/search")
    public String musicSearch(Pageable pageable, Model model,
                              @RequestParam("name") String name) {
        int count = musicService.countByNameLike(name);
        int length = PageableUtil.getLength(count,PAGE_SIZE);
        pageable = PageableUtil.getChecked(pageable,length);
        model.addAttribute("allMusic",musicService.getAllByNameContains(name,pageable));
        model.addAttribute("length",length);
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("name",name);
        return MUSIC_SEARCH;
    }


    @GetMapping("/category/{id}")
    public String searchCategory(@PathVariable("id")String strId, Pageable pageable,
                                 Model model){
        LOGGER.debug("catId : {}",strId);
        try {
            Category category;
            if((category = categoryService.findById(Integer.parseInt(strId))) == null){
                return "redirect:/";
            }
            int length = PageableUtil.getLength(musicService.countByCategoryId(category.getId()),PAGE_SIZE);
            pageable = PageableUtil.getChecked(pageable,length);
            model.addAttribute("allMusic",musicService.getAllByCategoryId(category.getId(),pageable));
            model.addAttribute("pageNumber",pageable.getPageNumber());
            model.addAttribute("length",length);
            model.addAttribute("category",category);
        }catch (NumberFormatException e){
            return "redirect:/";
        }
        return MUSIC_CATEGORY;
    }
}
