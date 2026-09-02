package com.retailx.inventoryservice.exception;

public class DuplicateReservationException extends  RuntimeException{
    public DuplicateReservationException(String message){
        super(message);
    }
}
