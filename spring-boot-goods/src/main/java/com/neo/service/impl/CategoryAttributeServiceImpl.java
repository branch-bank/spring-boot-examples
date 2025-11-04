package com.neo.service.impl;

import com.neo.model.CategoryAttribute;
import com.neo.repository.CategoryAttributeRepository;
import com.neo.service.CategoryAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

    @Autowired
    private CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public List<CategoryAttribute> getAllCategoryAttributes() {
        return categoryAttributeRepository.findAll();
    }

    @Override
    public List<CategoryAttribute> getCategoryAttributesByCategoryId(Long categoryId) {
        return categoryAttributeRepository.findByCategoryIdOrderBySortAsc(categoryId);
    }

    @Override
    public CategoryAttribute getCategoryAttributeById(Long id) {
        return categoryAttributeRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryAttribute saveCategoryAttribute(CategoryAttribute categoryAttribute) {
        return categoryAttributeRepository.save(categoryAttribute);
    }

    @Override
    public void deleteCategoryAttribute(Long id) {
        categoryAttributeRepository.deleteById(id);
    }

}