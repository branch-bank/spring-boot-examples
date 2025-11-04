package com.neo.repository;

import com.neo.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    List<Goods> findByIsOnSaleTrueOrderByCreateTimeDesc();

    List<Goods> findByCategoryIdAndIsOnSaleTrueOrderByCreateTimeDesc(Long categoryId);

    List<Goods> findBySupplierAndIsOnSaleTrueOrderByCreateTimeDesc(String supplier);

}