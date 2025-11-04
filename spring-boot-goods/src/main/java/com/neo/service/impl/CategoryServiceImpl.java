package com.neo.service.impl;

import com.neo.model.Category;
import com.neo.repository.CategoryRepository;
import com.neo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findByParentIsNullOrderBySortAsc();
    }

    @Override
    public List<Category> getChildrenCategories(Long parentId) {
        return categoryRepository.findByParentIdOrderBySortAsc(parentId);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category saveCategory(Category category) {
        // 设置分类级别
        if (category.getParent() == null) {
            category.setLevel(1);
        } else {
            category.setLevel(category.getParent().getLevel() + 1);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}