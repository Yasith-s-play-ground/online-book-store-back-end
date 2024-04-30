package com.backend.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bookstore.entity.Category;

@Service
public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

}
