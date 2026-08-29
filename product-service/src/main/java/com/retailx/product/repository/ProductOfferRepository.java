package com.retailx.product.repository;

import com.retailx.product.entity.ProductOffer;
import com.retailx.product.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOfferRepository extends JpaRepository<ProductOffer,Long> {
    boolean existsByProductIdAndVendorId(Long productId,Long vendorId);
    List<ProductOffer> findAllByProductIdAndStatus( Long productId,OfferStatus status);
}
