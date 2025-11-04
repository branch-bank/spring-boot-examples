package com.neo.repository;

import com.neo.model.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long> {

    List<CategoryAttribute> findByCategoryIdOrderBySortAsc(Long categoryId);

    List<CategoryAttribute> findByCategoryIdAndEnabledTrueOrderBySortAsc(Long categoryId);

}