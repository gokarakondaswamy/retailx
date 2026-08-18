package com.retailx.product.repository;

import com.retailx.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category,Long>{
}
