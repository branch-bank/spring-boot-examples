package com.neo.controller;

import com.neo.model.GoodsAttribute;
import com.neo.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-attributes")
public class GoodsAttributeController {

    @Autowired
    private GoodsAttributeService goodsAttributeService;

    @GetMapping
    public ResponseEntity<List<GoodsAttribute>> getAllGoodsAttributes() {
        List<GoodsAttribute> attributes = goodsAttributeService.getAllGoodsAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<List<GoodsAttribute>> getGoodsAttributesByGoodsId(@PathVariable Long goodsId) {
        List<GoodsAttribute> attributes = goodsAttributeService.getGoodsAttributesByGoodsId(goodsId);
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsAttribute> getGoodsAttributeById(@PathVariable Long id) {
        GoodsAttribute attribute = goodsAttributeService.getGoodsAttributeById(id);
        if (attribute == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attribute, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GoodsAttribute> saveGoodsAttribute(@RequestBody GoodsAttribute attribute) {
        GoodsAttribute savedAttribute = goodsAttributeService.saveGoodsAttribute(attribute);
        return new ResponseEntity<>(savedAttribute, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodsAttribute> updateGoodsAttribute(@PathVariable Long id, @RequestBody GoodsAttribute attribute) {
        attribute.setId(id);
        GoodsAttribute updatedAttribute = goodsAttributeService.saveGoodsAttribute(attribute);
        return new ResponseEntity<>(updatedAttribute, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoodsAttribute(@PathVariable Long id) {
        goodsAttributeService.deleteGoodsAttribute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}