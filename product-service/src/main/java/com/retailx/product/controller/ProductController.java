package com.retailx.product.controller;

import com.retailx.product.dto.CreateProductRequest;
import com.retailx.product.dto.CreateProductResponse;
import com.retailx.product.dto.ProductOfferResponse;
import com.retailx.product.dto.ProductResponse;
import com.retailx.product.entity.Product;
import com.retailx.product.entity.ProductOffer;
import com.retailx.product.service.ProductOfferService;
import com.retailx.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private  final ProductService productService;
    private  final ProductOfferService productOfferService;

    public ProductController(ProductService productService,ProductOfferService productOfferService){

        this.productService=productService;
        this.productOfferService=productOfferService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct( @Valid @RequestBody CreateProductRequest request) {

        Long id = productService.createProduct(request).getId();

        CreateProductResponse response =
                new CreateProductResponse(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductByID(@PathVariable Long productId){
        Product product=productService.findProductByID(productId);
        ProductResponse response= new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        List<Product> products=productService.findAllProducts();
        List<ProductResponse> response= new ArrayList<>();
        for(Product item:products){
            ProductResponse productResponse =new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setDescription(item.getDescription());
            response.add(productResponse);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}/offers")
    public ResponseEntity<List<ProductOfferResponse>>findActiveOffersByProductId(@PathVariable Long productId){
        List<ProductOffer> activeOffers = productOfferService.findActiveOffersByProductId(productId);
        List<ProductOfferResponse>response=new ArrayList<>();
        for(ProductOffer item:activeOffers ){
            ProductOfferResponse productOfferResponse=new ProductOfferResponse();
            productOfferResponse.setProductId(item.getProduct().getId());
            productOfferResponse.setProductName(item.getProduct().getName());
            productOfferResponse.setDescription(item.getProduct().getDescription());
            productOfferResponse.setPrice(item.getPrice());
            productOfferResponse.setVendorId(item.getVendorId());
            productOfferResponse.setStatus(item.getStatus());
            response.add(productOfferResponse);
        }
        return ResponseEntity.ok(response);
    }

}
