package com.retailx.inventoryservice.repository;

import com.retailx.inventoryservice.entity.Inventory;
import com.retailx.inventoryservice.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {
    boolean existsByOrderIdAndOfferId(Long orderId, Long offerId);
}
