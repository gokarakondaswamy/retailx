package com.retailx.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateProductOfferRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long vendorId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;
}