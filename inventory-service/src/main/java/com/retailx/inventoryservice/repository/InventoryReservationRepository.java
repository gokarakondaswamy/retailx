package com.retailx.inventoryservice.repository;

import com.retailx.inventoryservice.entity.Inventory;
import com.retailx.inventoryservice.entity.InventoryReservation;
import com.retailx.inventoryservice.enums.ReservationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {
    boolean existsByOrderIdAndOfferId(Long orderId, Long offerId);


    Optional<InventoryReservation> findByOrderIdAndOfferId(
            Long orderId,
            Long offerId
    );

    List<InventoryReservation>
    findByStatusAndExpiresAtLessThanEqualOrderByExpiresAtAsc(
            ReservationStatus status,
            LocalDateTime expiresAt,
            Pageable pageable
    );
}
