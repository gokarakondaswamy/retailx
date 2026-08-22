package com.retailx.product.service;

import com.retailx.product.constants.ProductConstants;
import com.retailx.product.entity.Product;
import com.retailx.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.retailx.product.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public  ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public Product findProductByID(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format(ProductConstants.PRODUCT_NOT_FOUND, id)));
    }
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }
}
