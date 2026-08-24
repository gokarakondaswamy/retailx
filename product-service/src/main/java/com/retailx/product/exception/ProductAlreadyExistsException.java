package com.retailx.product.exception;

import com.retailx.common.exception.ResourceNotFoundException;

public class ProductAlreadyExistsException extends ResourceNotFoundException {

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}
