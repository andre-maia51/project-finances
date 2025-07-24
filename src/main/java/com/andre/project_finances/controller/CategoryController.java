package com.andre.project_finances.controller;

import com.andre.project_finances.domain.dto.CategoryDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO, Authentication auth) {
        User owner = (User) auth.getPrincipal();
        CategoryDTO response = this.categoryService.createCategory(categoryDTO, owner);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
