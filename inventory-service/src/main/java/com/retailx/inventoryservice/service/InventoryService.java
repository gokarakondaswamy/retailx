package com.retailx.inventoryservice.service;

import com.retailx.inventoryservice.constants.InventoryConstants;
import com.retailx.inventoryservice.dto.InventoryReservationResponse;
import com.retailx.inventoryservice.dto.ReserveInventoryRequest;
import com.retailx.inventoryservice.entity.Inventory;
import com.retailx.inventoryservice.entity.InventoryReservation;
import com.retailx.inventoryservice.enums.ReservationStatus;
import com.retailx.inventoryservice.exception.*;
import com.retailx.inventoryservice.repository.InventoryRepository;
import com.retailx.inventoryservice.repository.InventoryReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InventoryService {
    private  final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository inventoryReservationRepository;
    public InventoryService(InventoryRepository inventoryRepository,InventoryReservationRepository inventoryReservationRepository){
        this.inventoryRepository=inventoryRepository;
        this.inventoryReservationRepository=inventoryReservationRepository;
    }

    @Transactional
    public void reserveInventory(ReserveInventoryRequest request) {

        Inventory inventory = inventoryRepository
                .findByOfferIdForUpdate(request.getOfferId())
                .orElseThrow(
                        () -> new InventoryNotFoundException(
                                String.format(
                                        InventoryConstants.INVENTORY_NOT_FOUND,
                                        request.getOfferId()
                                )
                        )
                );

        boolean exists =
                inventoryReservationRepository.existsByOrderIdAndOfferId(
                        request.getOrderId(),
                        request.getOfferId()
                );

        if (exists) {
            throw new DuplicateReservationException(
                    String.format(
                            InventoryConstants.RESERVATION_ALREADY_EXISTS,
                            request.getOrderId(),
                            request.getOfferId()
                    )
            );
        }

        int availableQuantity =
                inventory.getTotalQuantity() - inventory.getReservedQuantity();

        if (availableQuantity < request.getQuantity()) {
            throw new InsufficientStockException(
                    String.format(
                            InventoryConstants.INSUFFICIENT_STOCK,
                            request.getOfferId(),
                            request.getQuantity(),
                            availableQuantity
                    )
            );
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + request.getQuantity()
        );

        InventoryReservation inventoryReservation= new InventoryReservation();
        inventoryReservation.setInventory(inventory);
        inventoryReservation.setOfferId(request.getOfferId());
        inventoryReservation.setOrderId(request.getOrderId());
        inventoryReservation.setQuantity(request.getQuantity());
        inventoryReservation.setStatus(ReservationStatus.RESERVED);
        LocalDateTime createdAt = LocalDateTime.now();
        inventoryReservation.setCreatedAt(createdAt);
        inventoryReservation.setExpiresAt(
                createdAt.plusMinutes(
                        InventoryConstants.RESERVATION_EXPIRY_MINUTES
                )
        );
        inventoryReservationRepository.save(inventoryReservation);

    }

    @Transactional
    public InventoryReservationResponse releaseInventory(Long orderId,Long offerId){

        Inventory inventory = inventoryRepository
                .findByOfferIdForUpdate(offerId)
                .orElseThrow(
                        () -> new InventoryNotFoundException(
                                String.format(
                                        InventoryConstants.INVENTORY_NOT_FOUND,
                                        offerId
                                )
                        )
                );
        InventoryReservation inventoryReservation=inventoryReservationRepository
                .findByOrderIdAndOfferId(orderId,offerId)
                .orElseThrow(
                        () ->
                                new ReservationNotFoundException(
                                        String.format(
                                                InventoryConstants.RESERVATION_NOT_FOUND,
                                                orderId,
                                                offerId
                                        )
                                )
                );
        if(inventoryReservation.getStatus() == ReservationStatus.RELEASED){
            InventoryReservationResponse response= new InventoryReservationResponse(
                    inventoryReservation.getOrderId(),
                    inventoryReservation.getOfferId(),
                    inventoryReservation.getStatus(),
                    String.format(InventoryConstants.RESERVATION_ALREADY_RELEASED,
                            orderId,
                            offerId)
            );
            return response;


        }
        else if(inventoryReservation.getStatus() == ReservationStatus.EXPIRED){
            InventoryReservationResponse response= new InventoryReservationResponse(
                    inventoryReservation.getOrderId(),
                    inventoryReservation.getOfferId(),
                    inventoryReservation.getStatus(),
                    String.format(InventoryConstants.RESERVATION_EXPIRED,
                            orderId,
                            offerId)
            );
            return response;

        }
        else if(inventoryReservation.getStatus() == ReservationStatus.CONFIRMED){
            throw  new InvalidReservationStateException(
                    String.format(
                            InventoryConstants.CONFIRMED_RESERVATION_CANNOT_BE_RELEASED,
                            orderId,
                            offerId
                    )
            );
        }
        if (inventory.getReservedQuantity()
                < inventoryReservation.getQuantity()) {

            throw new InventoryConsistencyException(
                    String.format(
                            InventoryConstants.INCONSISTENT_RESERVED_QUANTITY,
                            offerId,
                            inventory.getReservedQuantity(),
                            inventoryReservation.getQuantity()
                    )
            );
        }
        int realsedQuantity=inventory.getReservedQuantity()-inventoryReservation.getQuantity();
        inventory.setReservedQuantity(realsedQuantity);
        inventoryReservation.setStatus(ReservationStatus.RELEASED);

        InventoryReservationResponse response= new InventoryReservationResponse(
                inventoryReservation.getOrderId(),
                inventoryReservation.getOfferId(),
                inventoryReservation.getStatus(),
                String.format(InventoryConstants.RESERVATION_RELEASED,
                        orderId,
                        offerId)
        );
        return response;

    }


    @Transactional
    public void expireReservation(Long orderId,Long offerId){

        Inventory inventory = inventoryRepository
                .findByOfferIdForUpdate(offerId)
                .orElseThrow(
                        () -> new InventoryNotFoundException(
                                String.format(
                                        InventoryConstants.INVENTORY_NOT_FOUND,
                                        offerId
                                )
                        )
                );

        InventoryReservation inventoryReservation=inventoryReservationRepository
                .findByOrderIdAndOfferId(orderId,offerId)
                .orElseThrow(
                        () ->
                                new ReservationNotFoundException(
                                        String.format(
                                                InventoryConstants.RESERVATION_NOT_FOUND,
                                                orderId,
                                                offerId
                                        )
                                )
                );
        ReservationStatus status = inventoryReservation.getStatus();

        if( status != ReservationStatus.RESERVED  ){
            return;

        }

        LocalDateTime presentTime = LocalDateTime.now();
        if (inventoryReservation.getExpiresAt().isAfter(presentTime)) {
            return;
        }
        if (inventory.getReservedQuantity()
                < inventoryReservation.getQuantity()) {

            throw new InventoryConsistencyException(
                    String.format(
                            InventoryConstants.INCONSISTENT_RESERVED_QUANTITY,
                            offerId,
                            inventory.getReservedQuantity(),
                            inventoryReservation.getQuantity()
                    )
            );
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity()-inventoryReservation.getQuantity());
        inventoryReservation.setStatus(ReservationStatus.EXPIRED);

    }

}
