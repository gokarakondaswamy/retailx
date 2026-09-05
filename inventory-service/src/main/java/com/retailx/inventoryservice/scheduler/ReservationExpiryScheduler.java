package com.retailx.inventoryservice.scheduler;

import com.retailx.inventoryservice.constants.InventoryConstants;
import com.retailx.inventoryservice.entity.InventoryReservation;
import com.retailx.inventoryservice.enums.ReservationStatus;
import com.retailx.inventoryservice.repository.InventoryReservationRepository;
import com.retailx.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationExpiryScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(ReservationExpiryScheduler.class);

    private  final InventoryReservationRepository inventoryReservationRepository;

    private  final InventoryService inventoryService;

    public ReservationExpiryScheduler(InventoryReservationRepository inventoryReservationRepository,InventoryService inventoryService){

        this.inventoryService=inventoryService;
        this.inventoryReservationRepository=inventoryReservationRepository;
    }

    @Scheduled(
            fixedDelayString =
                    "${inventory.reservation-expiry-scan-delay-ms:60000}"
    )
    public void processExpiredReservations() {
        Pageable pageable = PageRequest.of(
                0,
                InventoryConstants.RESERVATION_EXPIRY_BATCH_SIZE
        );
        LocalDateTime presentTime= LocalDateTime.now();
        List<InventoryReservation> inventoryReservationList= inventoryReservationRepository
                .findByStatusAndExpiresAtLessThanEqualOrderByExpiresAtAsc(
                        ReservationStatus.RESERVED,
                        presentTime,
                        pageable
                );

        for(InventoryReservation item : inventoryReservationList){

            try{
                inventoryService.expireReservation(item.getOrderId(), item.getOfferId());
            }
            catch ( Exception e){
                log.error(
                        "Failed to expire reservation for orderId: {} and offerId: {}",
                        item.getOrderId(),
                        item.getOfferId(),
                        e
                );
            }
        }
    }


}
