package com.retailx.product.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private String name;
   @Column(length = 4000)
   private String description;
   @Column(nullable = false)
   private BigDecimal price;
   private  int quanity;
   @ManyToMany
   @JoinTable(
           name="product_category",
           joinColumns=@JoinColumn(name = "product_id"),
           inverseJoinColumns=@JoinColumn(name = "category_id")
   )
   private Set<Category> categories;
}
