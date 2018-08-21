package net.musicalWorld.controller;

import net.musicalWorld.model.Category;
import net.musicalWorld.page.Pages;
import net.musicalWorld.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoriesController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public @ResponseBody
    List<Category> categories(){
        return categoryService.getAll();
    }

    @GetMapping("/admin/category/add")
    public String addGet(Model model){
        model.addAttribute("category",new Category());
        return CATEGORY_ADD;
    }

    @PostMapping("/admin/category/add")
    public String addPost(@Valid Category category, BindingResult result){
        LOGGER.debug("category : {}",category);
        if(result.hasErrors()){
            return CATEGORY_ADD;
        }else {
            categoryService.add(category);
            return "redirect:/admin";
        }
    }
}