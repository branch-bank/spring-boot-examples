package com.neo.service.impl;

import com.neo.model.GoodsTag;
import com.neo.repository.GoodsTagRepository;
import com.neo.service.GoodsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsTagServiceImpl implements GoodsTagService {

    @Autowired
    private GoodsTagRepository goodsTagRepository;

    @Override
    public List<GoodsTag> getAllGoodsTags() {
        return goodsTagRepository.findAll();
    }

    @Override
    public GoodsTag getGoodsTagById(Long id) {
        return goodsTagRepository.findById(id).orElse(null);
    }

    @Override
    public GoodsTag saveGoodsTag(GoodsTag goodsTag) {
        return goodsTagRepository.save(goodsTag);
    }

    @Override
    public void deleteGoodsTag(Long id) {
        goodsTagRepository.deleteById(id);
    }

}