package com.retailx.inventoryservice.constants;

public class InventoryConstants {
    private InventoryConstants(){

    }
    public static  final String INVENTORY_NOT_FOUND=
            "Inventory not found for offerId: %d";

    public static final String RESERVATION_ALREADY_EXISTS =
            "Reservation already exists for orderId: %d and offerId: %d";
    public static final String INSUFFICIENT_STOCK =
            "Insufficient stock for offerId: %d. Requested: %d, Available: %d";
    public static final long RESERVATION_EXPIRY_MINUTES = 15;
}
