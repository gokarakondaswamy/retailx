package com.retailx.product.dto;

public class CreateProductOfferResponse {

    private Long offerId;

    public CreateProductOfferResponse(Long offerId) {
        this.offerId = offerId;
    }

    public Long getOfferId() {
        return offerId;
    }
}
