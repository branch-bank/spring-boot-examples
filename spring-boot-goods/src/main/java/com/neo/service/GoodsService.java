package com.neo.service;

import com.neo.model.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> getAllGoods();

    List<Goods> getOnSaleGoods();

    List<Goods> getGoodsByCategoryId(Long categoryId);

    List<Goods> getGoodsBySupplier(String supplier);

    Goods getGoodsById(Long id);

    Goods saveGoods(Goods goods);

    void deleteGoods(Long id);

}