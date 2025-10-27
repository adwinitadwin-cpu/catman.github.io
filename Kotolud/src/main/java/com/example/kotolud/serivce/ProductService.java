package com.example.kotolud.serivce;

import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO create(CreateProductDTO createProductDTO );

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();
}
