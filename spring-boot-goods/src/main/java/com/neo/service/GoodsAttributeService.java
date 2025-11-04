package com.neo.service;

import com.neo.model.GoodsAttribute;

import java.util.List;

public interface GoodsAttributeService {

    List<GoodsAttribute> getAllGoodsAttributes();

    List<GoodsAttribute> getGoodsAttributesByGoodsId(Long goodsId);

    GoodsAttribute getGoodsAttributeById(Long id);

    GoodsAttribute saveGoodsAttribute(GoodsAttribute goodsAttribute);

    void deleteGoodsAttribute(Long id);

}