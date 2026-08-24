package com.retailx.product.service;

import com.retailx.product.constants.ProductConstants;
import com.retailx.product.dto.CreateProductOfferRequest;
import com.retailx.product.entity.Product;
import com.retailx.product.entity.ProductOffer;
import com.retailx.product.enums.OfferStatus;
import com.retailx.product.exception.ProductNotFoundException;
import com.retailx.product.exception.ProductOfferAlreadyExistsException;
import com.retailx.product.repository.ProductOfferRepository;
import com.retailx.product.repository.ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductOfferService {
    private final ProductRepository productRepository;
    private  final ProductOfferRepository productOfferRepository;
    public ProductOfferService(ProductRepository productRepository,ProductOfferRepository productOfferRepository){
        this.productRepository=productRepository;
        this.productOfferRepository=productOfferRepository;
    }
    public ProductOffer createOffer(CreateProductOfferRequest request) {
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                String.format(
                                        ProductConstants.PRODUCT_NOT_FOUND,
                                        request.getProductId()
                                )
                        )
                );
        if(productOfferRepository.existsByProductIdAndVendorId(request.getProductId(), request.getVendorId())){
            throw new ProductOfferAlreadyExistsException(String.format(ProductConstants.PRODUCT_OFFER_ALREADY_EXISTS,request.getProductId(),request.getVendorId()));
        }
       ProductOffer offer= new ProductOffer();
        offer.setProduct(product);
        offer.setVendorId(request.getVendorId());
        offer.setPrice(request.getPrice());
        offer.setStatus(OfferStatus.ACTIVE);
        return  productOfferRepository.save(offer);
    }
}
