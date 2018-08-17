package net.musicalWorld.controller;

import net.musicalWorld.model.Category;
import net.musicalWorld.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public @ResponseBody
    List<Category> categories(){
        return categoryService.getAll();
    }
}
