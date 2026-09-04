package com.retailx.inventoryservice.exception;

public class InventoryConsistencyException extends RuntimeException{
    public InventoryConsistencyException(String message){
        super(message);
    }
}
