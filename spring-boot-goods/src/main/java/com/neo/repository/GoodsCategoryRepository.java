package com.neo.repository;

import com.neo.model.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

    List<GoodsCategory> findByEnabledTrueOrderBySortAsc();

}