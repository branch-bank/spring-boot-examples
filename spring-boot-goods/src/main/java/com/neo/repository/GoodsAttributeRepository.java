package com.neo.repository;

import com.neo.model.GoodsAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsAttributeRepository extends JpaRepository<GoodsAttribute, Long> {

    List<GoodsAttribute> findByGoodsId(Long goodsId);

}