package com.retailx.product.dto;

public class CreateProductResponse {
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CreateProductResponse(Long productId) {
        this.productId = productId;
    }
}
