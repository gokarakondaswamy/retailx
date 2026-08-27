package com.retailx.product.controller;



import com.retailx.product.dto.CreateProductRequest;
import com.retailx.product.dto.CreateProductResponse;
import com.retailx.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private  final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
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
    @PostMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("test ok");
    }

}
