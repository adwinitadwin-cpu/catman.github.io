package com.example.kotolud.mapper;

import com.example.kotolud.dto.category.CreateCategoryDTO;
import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.model.Category;
import com.example.kotolud.model.Product;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    /**
     * Преобразует CreateCategoryDTO в Category сущность
     */
    public Category toEntity(CreateCategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }


    /**
     * Преобразует Category сущность в CategoryResponseDTO
     */
    public CategoryResponseDTO toResponseDTO(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    /**
     * Обновляет существующую Category из CreateCategoryDTO
     */
    public Category updateEntity(CreateCategoryDTO dto, Category category) {
        if (dto == null || category == null) {
            return category;
        }

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }
}