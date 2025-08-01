package com.andre.project_finances.service;

import com.andre.project_finances.dto.CategoryDTO;
import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.infra.excepctions.BusinessRuleConflict;
import com.andre.project_finances.infra.excepctions.ResourceNotFoundException;
import com.andre.project_finances.repository.CategoryRepository;
import com.andre.project_finances.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
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

    public List<CategoryDTO> getCategoriesByUser(User user) {
        return this.categoryRepository.findCategoriesByUser(user)
                .stream()
                .map(category -> new CategoryDTO(category.name(), category.description()))
                .toList();
    }

    public CategoryDTO getCategoryByUserId(Long id) {
        Category category = this.getCategoryFromRepository(id);
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    @Transactional
    public CategoryDTO changeCategory(Long id, CategoryDTO categoryDTO) {
        Category category = this.getCategoryFromRepository(id);
        category.setName(categoryDTO.name());
        category.setDescription(categoryDTO.description());
        this.categoryRepository.save(category);
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    public Category getCategoryFromRepository(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = this.getCategoryFromRepository(id);
        if(this.transactionRepository.existsByCategory(category)) {
            throw new BusinessRuleConflict("Não é possível apagar uma categoria com transações associadas");
        } else {
            this.categoryRepository.delete(category);
        }
    }
}
