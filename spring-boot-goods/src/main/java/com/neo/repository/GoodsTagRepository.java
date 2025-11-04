package com.neo.repository;

import com.neo.model.GoodsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsTagRepository extends JpaRepository<GoodsTag, Long> {

    List<GoodsTag> findByEnabledTrueOrderBySortAsc();

}