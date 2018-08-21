package net.musicalWorld.service.impl;

import net.musicalWorld.model.Category;
import net.musicalWorld.repository.CategoryRepository;
import net.musicalWorld.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.isPresent() ? category.get() : null;
    }

    @Transactional
    public void add(Category category) {
        categoryRepository.save(category);
        LOGGER.debug("category saved");
    }
}
