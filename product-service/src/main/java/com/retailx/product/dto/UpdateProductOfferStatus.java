package com.retailx.product.dto;


import com.retailx.product.enums.OfferStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateProductOfferStatus {

    @NotNull
    private OfferStatus status;

    public OfferStatus  getStatus() {
        return status;
    }

    public void setStatus(OfferStatus  status) {
        this.status = status;
    }
}
