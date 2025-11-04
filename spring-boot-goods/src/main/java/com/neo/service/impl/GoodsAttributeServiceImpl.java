package com.neo.service.impl;

import com.neo.model.GoodsAttribute;
import com.neo.repository.GoodsAttributeRepository;
import com.neo.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeRepository goodsAttributeRepository;

    @Override
    public List<GoodsAttribute> getAllGoodsAttributes() {
        return goodsAttributeRepository.findAll();
    }

    @Override
    public List<GoodsAttribute> getGoodsAttributesByGoodsId(Long goodsId) {
        return goodsAttributeRepository.findByGoodsId(goodsId);
    }

    @Override
    public GoodsAttribute getGoodsAttributeById(Long id) {
        return goodsAttributeRepository.findById(id).orElse(null);
    }

    @Override
    public GoodsAttribute saveGoodsAttribute(GoodsAttribute goodsAttribute) {
        return goodsAttributeRepository.save(goodsAttribute);
    }

    @Override
    public void deleteGoodsAttribute(Long id) {
        goodsAttributeRepository.deleteById(id);
    }

}