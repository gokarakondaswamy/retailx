package com.retailx.product.exception;

import com.retailx.common.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message){
        super(message);
    }
}
