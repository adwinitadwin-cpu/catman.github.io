package com.example.kotolud.serivce.category;

import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.dto.category.CreateCategoryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO create (CreateCategoryDTO createCategoryDTO );
    CategoryResponseDTO findById (Long id);
    List<CategoryResponseDTO> findAll();

    void delete(Long id);


    CategoryResponseDTO  update(Long id,  CreateCategoryDTO createCategoryDTO);
}
