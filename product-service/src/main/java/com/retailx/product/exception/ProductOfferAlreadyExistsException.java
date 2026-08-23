package com.retailx.product.exception;

public class ProductOfferAlreadyExistsException extends  RuntimeException{
    public ProductOfferAlreadyExistsException(String message){
        super(message);
    }
}
