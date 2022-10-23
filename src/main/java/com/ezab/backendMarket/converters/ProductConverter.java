package com.ezab.backendMarket.converters;

import com.ezab.backendMarket.dtos.ProductDTO;
import com.ezab.backendMarket.entities.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO> {

    @Override
    public ProductDTO fromEntity(Product entity) {
        if (entity == null) return null;
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Product fromDTO(ProductDTO dto) {
        if (dto == null) return null;
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }
}
