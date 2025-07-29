package com.andre.project_finances.service;

import com.andre.project_finances.dto.CategoryDTO;
import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO, User user) {
        Category category = new Category(categoryDTO.name(), categoryDTO.description(), user);
        this.saveCategory(category);
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    public Optional<Category> findCategory(Long id) {
        return this.categoryRepository.findById(id);
    }
}
