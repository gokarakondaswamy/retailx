package com.retailx.inventoryservice.repository;

import com.retailx.inventoryservice.entity.Inventory;
import com.retailx.inventoryservice.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {
    boolean existsByOrderIdAndOfferId(Long orderId, Long offerId);


    Optional<InventoryReservation> findByOrderIdAndOfferId(
            Long orderId,
            Long offerId
    );
}
