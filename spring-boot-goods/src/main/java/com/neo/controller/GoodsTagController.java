package com.neo.controller;

import com.neo.model.GoodsTag;
import com.neo.service.GoodsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-tags")
public class GoodsTagController {

    @Autowired
    private GoodsTagService goodsTagService;

    @GetMapping
    public ResponseEntity<List<GoodsTag>> getAllGoodsTags() {
        List<GoodsTag> tags = goodsTagService.getAllGoodsTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsTag> getGoodsTagById(@PathVariable Long id) {
        GoodsTag tag = goodsTagService.getGoodsTagById(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GoodsTag> saveGoodsTag(@RequestBody GoodsTag tag) {
        GoodsTag savedTag = goodsTagService.saveGoodsTag(tag);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodsTag> updateGoodsTag(@PathVariable Long id, @RequestBody GoodsTag tag) {
        tag.setId(id);
        GoodsTag updatedTag = goodsTagService.saveGoodsTag(tag);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoodsTag(@PathVariable Long id) {
        goodsTagService.deleteGoodsTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}