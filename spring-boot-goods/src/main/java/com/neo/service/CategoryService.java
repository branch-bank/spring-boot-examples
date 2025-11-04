package com.neo.service;

import com.neo.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getRootCategories();

    List<Category> getChildrenCategories(Long parentId);

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteCategory(Long id);

}