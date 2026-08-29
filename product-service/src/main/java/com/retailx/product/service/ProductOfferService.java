package com.retailx.product.service;

import com.retailx.product.constants.ProductConstants;
import com.retailx.product.dto.CreateProductOfferRequest;
import com.retailx.product.dto.UpdateProductOfferPriceRequest;
import com.retailx.product.dto.UpdateProductOfferStatus;
import com.retailx.product.entity.Product;
import com.retailx.product.entity.ProductOffer;
import com.retailx.product.enums.OfferStatus;
import com.retailx.product.exception.ProductNotFoundException;
import com.retailx.product.exception.ProductOfferAlreadyExistsException;
import com.retailx.product.exception.ProductOfferNotFoundException;
import com.retailx.product.repository.ProductOfferRepository;
import com.retailx.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<ProductOffer> findActiveOffersByProductId(Long productId) {

        boolean productExists =productRepository.existsById(productId);

        if(!productExists ){
            throw new ProductNotFoundException(String.format(ProductConstants.PRODUCT_NOT_FOUND,productId));
        }
        return productOfferRepository.findAllByProductIdAndStatus(productId,OfferStatus.ACTIVE);

    }

    public ProductOffer updateProductOfferPrice(Long offerId, UpdateProductOfferPriceRequest productOffer){
        ProductOffer productoffer=productOfferRepository.findById(offerId).orElseThrow(() -> new ProductOfferNotFoundException(String.format(ProductConstants.PRODUCT_OFFER_NOT_FOUND,offerId)));
        productoffer.setPrice(productOffer.getPrice());
        return productOfferRepository.save(productoffer);
    }

    public ProductOffer updateProductOfferStatus(Long offerId, UpdateProductOfferStatus productOffer){
        ProductOffer productoffer=productOfferRepository.findById(offerId).orElseThrow(() -> new ProductOfferNotFoundException(String.format(ProductConstants.PRODUCT_OFFER_NOT_FOUND,offerId)));

            productoffer.setStatus(productOffer.getStatus());

        return productOfferRepository.save(productoffer);
    }

}
