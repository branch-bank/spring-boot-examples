package com.neo.service;

import com.neo.model.GoodsTag;

import java.util.List;

public interface GoodsTagService {

    List<GoodsTag> getAllGoodsTags();

    GoodsTag getGoodsTagById(Long id);

    GoodsTag saveGoodsTag(GoodsTag goodsTag);

    void deleteGoodsTag(Long id);

}