package com.example.kotolud.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String imageId;
    private String urlProduct;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
