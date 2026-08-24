package com.retailx.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class CreateProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private Set<Long> categoryIds;

    // getters/setters

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }
}
