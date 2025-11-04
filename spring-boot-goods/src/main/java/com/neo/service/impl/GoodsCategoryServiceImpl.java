package com.neo.service.impl;

import com.neo.model.GoodsCategory;
import com.neo.repository.GoodsCategoryRepository;
import com.neo.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    @Override
    public List<GoodsCategory> getAllGoodsCategories() {
        return goodsCategoryRepository.findAll();
    }

    @Override
    public GoodsCategory getGoodsCategoryById(Long id) {
        return goodsCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public GoodsCategory saveGoodsCategory(GoodsCategory goodsCategory) {
        return goodsCategoryRepository.save(goodsCategory);
    }

    @Override
    public void deleteGoodsCategory(Long id) {
        goodsCategoryRepository.deleteById(id);
    }

}