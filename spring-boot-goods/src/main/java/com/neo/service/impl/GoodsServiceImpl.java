package com.neo.service.impl;

import com.neo.model.Goods;
import com.neo.repository.GoodsRepository;
import com.neo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    @Override
    public List<Goods> getOnSaleGoods() {
        return goodsRepository.findByIsOnSaleTrueOrderByCreateTimeDesc();
    }

    @Override
    public List<Goods> getGoodsByCategoryId(Long categoryId) {
        return goodsRepository.findByCategoryIdAndIsOnSaleTrueOrderByCreateTimeDesc(categoryId);
    }

    @Override
    public List<Goods> getGoodsBySupplier(String supplier) {
        return goodsRepository.findBySupplierAndIsOnSaleTrueOrderByCreateTimeDesc(supplier);
    }

    @Override
    public Goods getGoodsById(Long id) {
        return goodsRepository.findById(id).orElse(null);
    }

    @Override
    public Goods saveGoods(Goods goods) {
        if (goods.getId() == null) {
            goods.setCreateTime(new Date());
        }
        goods.setUpdateTime(new Date());
        return goodsRepository.save(goods);
    }

    @Override
    public void deleteGoods(Long id) {
        goodsRepository.deleteById(id);
    }

}