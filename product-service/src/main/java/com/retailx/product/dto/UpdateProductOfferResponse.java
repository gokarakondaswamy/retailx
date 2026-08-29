package com.retailx.product.dto;

public class UpdateProductOfferResponse {
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public UpdateProductOfferResponse(Long productId) {
        this.productId = productId;
    }
}
