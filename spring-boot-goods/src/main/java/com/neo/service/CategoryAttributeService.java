package com.neo.service;

import com.neo.model.CategoryAttribute;

import java.util.List;

public interface CategoryAttributeService {

    List<CategoryAttribute> getAllCategoryAttributes();

    List<CategoryAttribute> getCategoryAttributesByCategoryId(Long categoryId);

    CategoryAttribute getCategoryAttributeById(Long id);

    CategoryAttribute saveCategoryAttribute(CategoryAttribute categoryAttribute);

    void deleteCategoryAttribute(Long id);

}