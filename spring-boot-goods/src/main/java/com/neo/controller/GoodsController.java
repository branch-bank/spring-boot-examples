package com.neo.controller;

import com.neo.model.Goods;
import com.neo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public ResponseEntity<List<Goods>> getAllGoods() {
        List<Goods> goods = goodsService.getAllGoods();
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @GetMapping("/on-sale")
    public ResponseEntity<List<Goods>> getOnSaleGoods() {
        List<Goods> goods = goodsService.getOnSaleGoods();
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Goods>> getGoodsByCategoryId(@PathVariable Long categoryId) {
        List<Goods> goods = goodsService.getGoodsByCategoryId(categoryId);
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @GetMapping("/supplier/{supplier}")
    public ResponseEntity<List<Goods>> getGoodsBySupplier(@PathVariable String supplier) {
        List<Goods> goods = goodsService.getGoodsBySupplier(supplier);
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long id) {
        Goods goods = goodsService.getGoodsById(id);
        if (goods == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Goods> saveGoods(@RequestBody Goods goods) {
        Goods savedGoods = goodsService.saveGoods(goods);
        return new ResponseEntity<>(savedGoods, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Long id, @RequestBody Goods goods) {
        goods.setId(id);
        Goods updatedGoods = goodsService.saveGoods(goods);
        return new ResponseEntity<>(updatedGoods, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}