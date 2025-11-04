package com.neo.controller;

import com.neo.model.GoodsCategory;
import com.neo.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-categories")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping
    public ResponseEntity<List<GoodsCategory>> getAllGoodsCategories() {
        List<GoodsCategory> categories = goodsCategoryService.getAllGoodsCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsCategory> getGoodsCategoryById(@PathVariable Long id) {
        GoodsCategory category = goodsCategoryService.getGoodsCategoryById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GoodsCategory> saveGoodsCategory(@RequestBody GoodsCategory category) {
        GoodsCategory savedCategory = goodsCategoryService.saveGoodsCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodsCategory> updateGoodsCategory(@PathVariable Long id, @RequestBody GoodsCategory category) {
        category.setId(id);
        GoodsCategory updatedCategory = goodsCategoryService.saveGoodsCategory(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoodsCategory(@PathVariable Long id) {
        goodsCategoryService.deleteGoodsCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}