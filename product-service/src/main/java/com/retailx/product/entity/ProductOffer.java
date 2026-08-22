package com.retailx.product.entity;

import com.retailx.product.enums.OfferStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_offer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_vendor",
                        columnNames = {"product_id", "vendor_id"}
                )
        }
)
public class ProductOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
