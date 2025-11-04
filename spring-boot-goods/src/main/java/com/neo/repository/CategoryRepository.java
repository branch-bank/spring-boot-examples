package com.neo.repository;

import com.neo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNullOrderBySortAsc();

    List<Category> findByParentIdOrderBySortAsc(Long parentId);

    List<Category> findByEnabledTrueOrderBySortAsc();

}