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
    public static final String RESERVATION_NOT_FOUND =
            "Reservation not found for orderId: %d and offerId: %d";

    public static final String RESERVATION_RELEASED =
            "Reservation released for orderId: %d and offerId: %d";
    public static final String RESERVATION_ALREADY_RELEASED =
            "Reservation  already released for orderId: %d and offerId: %d";
    public static final String RESERVATION_EXPIRED =
            "Reservation expired for orderId: %d and offerId: %d";
    public static final String CONFIRMED_RESERVATION_CANNOT_BE_RELEASED =
            "Confirmed reservation cannot be released for orderId: %d and offerId: %d";

    public static final String INCONSISTENT_RESERVED_QUANTITY =
            "Inventory inconsistency for offerId: %d. Reserved quantity: %d, reservation quantity: %d";

    public static final String RESERVATION_CONFIRMED =
            "Reservation confirmed for orderId: %d and offerId: %d";

    public static final String RESERVATION_ALREADY_CONFIRMED =
            "Reservation already confirmed for orderId: %d and offerId: %d";

    public static final String RELEASED_RESERVATION_CANNOT_BE_CONFIRMED =
            "Released reservation cannot be confirmed for orderId: %d and offerId: %d";

    public static final String EXPIRED_RESERVATION_CANNOT_BE_CONFIRMED =
            "Expired reservation cannot be confirmed for orderId: %d and offerId: %d";

    public static final String INCONSISTENT_CONFIRMATION_QUANTITY =
            "Inventory inconsistency for offerId: %d. Total: %d, Reserved: %d, Reservation: %d";

    public static final String RESERVATION_CREATED =
            "Inventory reserved for orderId: %d and offerId: %d";

    public static final String INTERNAL_SERVER_ERROR =
            "An unexpected internal server error occurred";

    public static final long RESERVATION_EXPIRY_MINUTES = 15;

    public static final int RESERVATION_EXPIRY_BATCH_SIZE = 100;

}
