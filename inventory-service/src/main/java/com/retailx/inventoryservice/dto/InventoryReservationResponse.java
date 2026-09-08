package com.retailx.inventoryservice.dto;

import com.retailx.inventoryservice.enums.ReservationStatus;

public class InventoryReservationResponse {

    private final Long orderId;
    private final Long offerId;
    private final ReservationStatus status;

    public InventoryReservationResponse(
            Long orderId,
            Long offerId,
            ReservationStatus status) {

        this.orderId = orderId;
        this.offerId = offerId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

}