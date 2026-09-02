package com.retailx.inventoryservice.Service;

import com.retailx.inventoryservice.constants.InventoryConstants;
import com.retailx.inventoryservice.dto.ReserveInventoryRequest;
import com.retailx.inventoryservice.entity.Inventory;
import com.retailx.inventoryservice.exception.DuplicateReservationException;
import com.retailx.inventoryservice.exception.InsufficientStockException;
import com.retailx.inventoryservice.exception.InventoryNotFoundException;
import com.retailx.inventoryservice.repository.InventoryRepository;
import com.retailx.inventoryservice.repository.InventoryReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    }

}
