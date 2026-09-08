package com.retailx.inventoryservice.exception;

public class ReservationExpiredException  extends  RuntimeException{
    public ReservationExpiredException(String message){
        super(message);
    }
}
