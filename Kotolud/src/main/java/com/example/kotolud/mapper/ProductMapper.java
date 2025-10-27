package com.example.kotolud.mapper;

import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import org.springframework.stereotype.Component;

import com.example.kotolud.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    /**
     * Преобразует CreateProductDTO в Product сущность
     */
    public Product toEntity(CreateProductDTO dto) {
        if (dto == null) {
            return null;
        }

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .urlProduct(dto.getUrlProduct())
                .build();
    }

    /**
     * Преобразует Product сущность в ProductResponseDTO
     */
    public ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .imageId(product.getImageId())
                .urlProduct(product.getUrlProduct())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /**
     * Обновляет существующий Product из CreateProductDTO
     */
    public Product updateEntity(CreateProductDTO dto, Product product) {
        if (dto == null || product == null) {
            return product;
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setUrlProduct(dto.getUrlProduct());

        return product;
    }
}