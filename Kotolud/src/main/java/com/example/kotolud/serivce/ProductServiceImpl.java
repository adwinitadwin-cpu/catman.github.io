package com.example.kotolud.serivce;

import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.dto.product.UpdateProductDTO;
import com.example.kotolud.mapper.ProductMapper;
import com.example.kotolud.model.Category;
import com.example.kotolud.model.Product;
import com.example.kotolud.repository.CategoryRepository;
import com.example.kotolud.repository.ProductRepository;
import com.example.kotolud.serivce.ProductService;
import com.example.kotolud.util.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;

    @Override
    public ProductResponseDTO create(CreateProductDTO createProductDTO) {
        Product product = productMapper.toEntity(createProductDTO);

        if (createProductDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(createProductDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            product.getCategories().add(category);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)  // ✅ Только для чтения
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт с ID " + id + " не найден"));
        return productMapper.toResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)  // ✅ Только для чтения
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO update(Long id, UpdateProductDTO updateProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        // Обновляем основные поля
        product.setName(updateProductDTO.getName());
        product.setDescription(updateProductDTO.getDescription());
        product.setUrlProduct(updateProductDTO.getUrlProduct());

        // Обновляем категорию если передана
        if (updateProductDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateProductDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));

            // Очищаем старые категории и добавляем новую
            product.getCategories().clear();
            product.getCategories().add(category);
        }

        Product updated = productRepository.save(product);
        log.info("✅ Product updated: {}", id);

        return productMapper.toResponseDTO(updated);
    }




    @Override
    public ProductResponseDTO uploadPicture(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        try {
            if (product.getImageId() != null && !product.getImageId().isEmpty()) {
                storageService.deleteImage(product.getImageId());
            }

            StorageService.StorageResult result = storageService.uploadImage(file);

            product.setImageUrl(result.getUrl());
            product.setImageId(result.getImageId());

            Product updated = productRepository.save(product);
            log.info("✅ Image uploaded for product: {}", productId);

            return productMapper.toResponseDTO(updated);

        } catch (IOException e) {
            log.error("❌ Error uploading image: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductResponseDTO deletePicture(Long productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        try {
            if (product.getImageId() != null && !product.getImageId().isEmpty()) {
                boolean deleted = storageService.deleteImage(product.getImageId());

                if (deleted) {
                    product.setImageUrl(null);
                    product.setImageId(null);

                    Product updated = productRepository.save(product);
                    log.info("✅ Image deleted for product: {}", productId);

                    return productMapper.toResponseDTO(updated);
                } else {
                    throw new IOException("Failed to delete image from storage");
                }
            }

            return productMapper.toResponseDTO(product);

        } catch (IOException e) {
            log.error("❌ Error deleting image: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteProductById(Long id) {
      productRepository.deleteById(id);

    }

    @Override
    public Long count() {
       return productRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getLimitProduct(int limit) {
        return productRepository.findAll()  // ✅ Получаем все продукты
                .stream()
                .limit(limit)               // ✅ Ограничиваем количество
                .map(productMapper::toResponseDTO)  // ✅ Преобразуем в DTO
                .toList();
    }
}