package com.andre.project_finances.controller;

import com.andre.project_finances.dto.CategoryDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategoriesByUser(Authentication auth) {
        User owner = (User) auth.getPrincipal();
        List<CategoryDTO> response = this.categoryService.getCategoriesByUser(owner);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO response = this.categoryService.getCategoryByUserId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> changeCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO response = this.categoryService.changeCategory(id, categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
