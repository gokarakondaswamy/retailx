package com.retailx.inventoryservice.handler;

import com.retailx.common.dto.ApiResponse;
import com.retailx.inventoryservice.constants.InventoryConstants;
import com.retailx.inventoryservice.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({InventoryNotFoundException.class, ReservationNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> handleNotFound(RuntimeException exception){
        ApiResponse<Void> response = new ApiResponse<>(
                false,
                exception.getMessage(),
                null
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
    @ExceptionHandler({
            DuplicateReservationException.class,
            InsufficientStockException.class,
            ReservationExpiredException.class,
            InvalidReservationStateException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleConflict( RuntimeException exception) {

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                exception.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
    @ExceptionHandler(InventoryConsistencyException.class)
    public ResponseEntity<ApiResponse<Void>> handleInventoryConsistency( InventoryConsistencyException exception) {

        log.error("Inventory consistency error", exception);

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                InventoryConstants.INTERNAL_SERVER_ERROR,
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation( MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Invalid request");

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                message,
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(
            Exception exception) {

        log.error("Unexpected error while processing inventory request", exception);

        ApiResponse<Void> response = new ApiResponse<>(
                false,
                InventoryConstants.INTERNAL_SERVER_ERROR,
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}