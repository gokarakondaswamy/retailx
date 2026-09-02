package com.retailx.inventoryservice.exception;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String message){
        super(message);
    }
}
