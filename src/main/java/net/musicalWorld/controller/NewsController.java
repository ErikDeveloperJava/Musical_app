package net.musicalWorld.controller;

import net.musicalWorld.form.NewsRequestForm;
import net.musicalWorld.model.News;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.NewsService;
import net.musicalWorld.util.FileUtil;
import net.musicalWorld.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class NewsController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private NewsService newsService;

    @GetMapping("/admin/news/add")
    public String addGet(Model model) {
        model.addAttribute("form", new NewsRequestForm());
        return NEWS_ADD;
    }

    @PostMapping("/admin/news/add")
    public String addPost(@ModelAttribute("form") @Valid NewsRequestForm form, BindingResult result) {
        LOGGER.debug("form : {}",form);
        if (result.hasErrors()) {
            return NEWS_ADD;
        }else if(form.getImage().isEmpty() || !fileUtil.isValidImgFormat(form.getImage().getContentType())){
            result.addError(new FieldError("form","image",""));
            return NEWS_ADD;
        }else {
            News news = News.builder()
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .createdDate(new Date())
                    .imgUrl("")
                    .build();
            newsService.add(news,form.getImage());
            return "redirect:/news";
        }
    }

    @GetMapping("/news")
    public String news(Pageable pageable, Model model,@RequestParam(value = "token",required = false,defaultValue = "NONE")String token){
        int count = newsService.count();
        int length = PageableUtil.getLength(count, pageable.getPageSize());
        pageable = PageableUtil.getChecked(pageable,length);
        model.addAttribute("allNews",newsService.getAll(pageable));
        model.addAttribute("length",length);
        model.addAttribute("pageNumber",pageable.getPageNumber());
        return token.equals("NONE") ? NEWS : NEWS_JS;
    }

    @PostMapping("/admin/news/delete")
    public @ResponseBody
    boolean delete(@RequestParam("newsId")int newsId){
        LOGGER.debug("newsId : {}",newsId);
        newsService.deleteById(newsId);
        return true;
    }

    @GetMapping("/news/{id}")
    public String oneNews(@PathVariable("id")String strId,Model model){
        LOGGER.debug("newsId : {}",strId);
        int id;
        try {
            if (!newsService.existsById((id = Integer.parseInt(strId)))) {
                return "redirect:/news";
            }
            model.addAttribute("news",newsService.getById(id));
            model.addAttribute("likeNews",newsService.getTop5AndUnlessId(id));
        }catch (NumberFormatException e){
            return "redirect:/news";
        }
        return NEWS_DETAIL;
    }

}
