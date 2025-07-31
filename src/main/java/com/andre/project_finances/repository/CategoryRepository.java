package com.andre.project_finances.repository;

import com.andre.project_finances.domain.entities.Category;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryDTO> findCategoriesByUser(User user);
}
