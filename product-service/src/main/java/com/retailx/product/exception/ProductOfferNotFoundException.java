package com.retailx.product.exception;

public class ProductOfferNotFoundException extends  RuntimeException{
    public ProductOfferNotFoundException(String message){
        super(message);
    }
}
