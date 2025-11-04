package com.neo.controller;

import com.neo.model.CategoryAttribute;
import com.neo.service.CategoryAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-attributes")
public class CategoryAttributeController {

    @Autowired
    private CategoryAttributeService categoryAttributeService;

    @GetMapping
    public ResponseEntity<List<CategoryAttribute>> getAllCategoryAttributes() {
        List<CategoryAttribute> attributes = categoryAttributeService.getAllCategoryAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryAttribute>> getCategoryAttributesByCategoryId(@PathVariable Long categoryId) {
        List<CategoryAttribute> attributes = categoryAttributeService.getCategoryAttributesByCategoryId(categoryId);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryAttribute> getCategoryAttributeById(@PathVariable Long id) {
        CategoryAttribute attribute = categoryAttributeService.getCategoryAttributeById(id);
        if (attribute == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attribute, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryAttribute> saveCategoryAttribute(@RequestBody CategoryAttribute attribute) {
        CategoryAttribute savedAttribute = categoryAttributeService.saveCategoryAttribute(attribute);
        return new ResponseEntity<>(savedAttribute, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryAttribute> updateCategoryAttribute(@PathVariable Long id, @RequestBody CategoryAttribute attribute) {
        attribute.setId(id);
        CategoryAttribute updatedAttribute = categoryAttributeService.saveCategoryAttribute(attribute);
        return new ResponseEntity<>(updatedAttribute, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryAttribute(@PathVariable Long id) {
        categoryAttributeService.deleteCategoryAttribute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}