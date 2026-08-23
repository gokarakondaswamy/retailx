package com.retailx.product.repository;

import com.retailx.product.entity.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferRepository extends JpaRepository<ProductOffer,Long> {
    boolean existsByProductIdAndVendorId(Long productId,Long vendorId);
}
