package com.retailx.inventoryservice.controller;

import com.retailx.common.dto.ApiResponse;
import com.retailx.inventoryservice.constants.InventoryConstants;
import com.retailx.inventoryservice.dto.InventoryReservationResponse;
import com.retailx.inventoryservice.dto.ReserveInventoryRequest;
import com.retailx.inventoryservice.enums.ReservationStatus;
import com.retailx.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private  final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){

        this.inventoryService=inventoryService;
    }

    @PostMapping("/reservations/{orderId}/offers/{offerId}/confirm")
    public ResponseEntity<ApiResponse<InventoryReservationResponse>>  confirmReservation(@PathVariable Long orderId,@PathVariable Long offerId){
        InventoryReservationResponse response =
                inventoryService.confirmReservation(
                        orderId,
                        offerId
                );

        ApiResponse<InventoryReservationResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        String.format(
                                InventoryConstants.RESERVATION_CONFIRMED,
                                orderId,
                                offerId
                        ),
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/reservations/{orderId}/offers/{offerId}/release")
    public ResponseEntity<ApiResponse<InventoryReservationResponse>> releaseReservation(@PathVariable Long orderId,@PathVariable Long offerId){
        InventoryReservationResponse response =
                inventoryService.releaseInventory(
                        orderId,
                        offerId
                );

        String message =
                response.getStatus() == ReservationStatus.EXPIRED
                        ? String.format(
                        InventoryConstants.RESERVATION_EXPIRED,
                        orderId,
                        offerId
                )
                        : String.format(
                        InventoryConstants.RESERVATION_RELEASED,
                        orderId,
                        offerId
                );

        ApiResponse<InventoryReservationResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        message,
                        response
                );

        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/reservations")
    public ResponseEntity<ApiResponse<InventoryReservationResponse>> reserveInventory(@Valid @RequestBody ReserveInventoryRequest request){

        InventoryReservationResponse response = inventoryService.reserveInventory(request);
        ApiResponse<InventoryReservationResponse> apiResponse =
                new ApiResponse<>(
                        true,
                        String.format(
                                InventoryConstants.RESERVATION_CREATED,
                                response.getOrderId(),
                                response.getOfferId()
                        ),
                        response
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);


    }
}
