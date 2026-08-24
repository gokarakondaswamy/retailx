package com.retailx.product.exception;

public class CategoryNotFoundException extends  RuntimeException{
    public CategoryNotFoundException(String messsage){
        super(messsage);
    }
}
