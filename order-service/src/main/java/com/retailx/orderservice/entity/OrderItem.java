package com.retailx.orderservice.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(
        name = "order_items",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_order_offer",
                        columnNames = {"order_id", "offer_id"}
                )
        }
)
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    @Column(name="offer_id",nullable = false)
    private Long offerId;

    @Column(name="product_id",nullable = false)
    private Long productId;

    private String productName;

    private Long vendorId;

    private String vendorName;

    private int quantity;

    private BigDecimal unitPriceAtPurchase;

    private BigDecimal itemTotal;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPriceAtPurchase() {
        return unitPriceAtPurchase;
    }

    public void setUnitPriceAtPurchase(BigDecimal unitPriceAtPurchase) {
        this.unitPriceAtPurchase = unitPriceAtPurchase;
    }

    public BigDecimal getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

