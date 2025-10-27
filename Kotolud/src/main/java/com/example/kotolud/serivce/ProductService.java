package com.example.kotolud.serivce;
import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.dto.product.UpdateProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface ProductService {
    ProductResponseDTO create(CreateProductDTO createProductDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();


    ProductResponseDTO update(Long id, UpdateProductDTO updateProductDTO);


    /**
     * Загружает изображение для продукта
     */
    ProductResponseDTO uploadPicture(Long productId, MultipartFile file) throws IOException;

    /**
     * Удаляет изображение продукта
     */
    ProductResponseDTO deletePicture(Long productId) throws IOException;


    void deleteProductById(Long id);


    Long count();
}