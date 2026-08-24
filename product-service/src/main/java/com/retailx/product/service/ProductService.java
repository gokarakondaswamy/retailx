package com.retailx.product.service;

import com.retailx.product.constants.ProductConstants;
import com.retailx.product.dto.CreateProductRequest;
import com.retailx.product.entity.Category;
import com.retailx.product.entity.Product;
import com.retailx.product.exception.CategoryNotFoundException;
import com.retailx.product.exception.ProductAlreadyExistsException;
import com.retailx.product.repository.CategoryRepository;
import com.retailx.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.retailx.product.exception.ProductNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private  final CategoryRepository categoryRepository;

    public  ProductService(ProductRepository productRepository,CategoryRepository categoryRepository){

        this.productRepository=productRepository;

        this.categoryRepository=categoryRepository;
    }

    public Product findProductByID(Long id){
        return productRepository.findById(id).
                orElseThrow(() ->
                        new ProductNotFoundException
                                (String.format(ProductConstants.PRODUCT_NOT_FOUND, id)));

    }

    public List<Product> findAllProducts(){

        return productRepository.findAll();
    }

    public Product createProduct(CreateProductRequest request) {
        if(productRepository.existsByNameIgnoreCase(request.getName())){
            throw new ProductAlreadyExistsException(String.format(ProductConstants.PRODUCT_ALREADY_EXISTS,request.getName()));
        }

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        if(categories.size()!=request.getCategoryIds().size()){

            throw new CategoryNotFoundException(ProductConstants.CATEGORY_NOT_FOUND);

        }
        Product product=new Product();
        product.setName(request.getName());
        product.setCategories(new HashSet<>(categories));
        product.setDescription(request.getDescription());
        return productRepository.save(product);

    }
}
