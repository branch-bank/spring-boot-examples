package com.neo.service;

import com.neo.model.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService {

    List<GoodsCategory> getAllGoodsCategories();

    GoodsCategory getGoodsCategoryById(Long id);

    GoodsCategory saveGoodsCategory(GoodsCategory goodsCategory);

    void deleteGoodsCategory(Long id);

}