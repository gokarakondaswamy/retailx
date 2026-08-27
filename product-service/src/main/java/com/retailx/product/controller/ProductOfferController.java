package com.retailx.product.controller;

import com.retailx.product.dto.CreateProductOfferRequest;
import com.retailx.product.dto.CreateProductOfferResponse;
import com.retailx.product.service.ProductOfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
