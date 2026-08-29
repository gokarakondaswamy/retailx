package com.retailx.product.controller;

import com.retailx.product.dto.*;
import com.retailx.product.entity.ProductOffer;
import com.retailx.product.service.ProductOfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product-offers")
public class ProductOfferController {

    private  final ProductOfferService productOfferService;
    public ProductOfferController(ProductOfferService productOfferService){
        this.productOfferService=productOfferService;
    }
    @PostMapping
    public ResponseEntity<CreateProductOfferResponse>  createProductOffer(@Valid @RequestBody CreateProductOfferRequest request){
        Long id=productOfferService.createOffer(request).getId();
        CreateProductOfferResponse response=new CreateProductOfferResponse(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{offerId}/price")
    public ResponseEntity<UpdateProductOfferResponse> updateProductOfferPrice(@PathVariable Long offerId, @Valid @RequestBody UpdateProductOfferPriceRequest request){
        Long updatedOfferId =productOfferService.updateProductOfferPrice(offerId,request).getId();
        UpdateProductOfferResponse response=new UpdateProductOfferResponse(updatedOfferId );
        return ResponseEntity.ok(response);

    }
    @PatchMapping("/{offerId}/status")
    public ResponseEntity<UpdateProductOfferResponse> updateProductStatus(@PathVariable Long offerId, @Valid @RequestBody UpdateProductOfferStatus request){
        Long updatedOfferId =productOfferService.updateProductOfferStatus(offerId,request).getId();
        UpdateProductOfferResponse response=new UpdateProductOfferResponse(updatedOfferId );
        return ResponseEntity.ok(response);

    }

}
