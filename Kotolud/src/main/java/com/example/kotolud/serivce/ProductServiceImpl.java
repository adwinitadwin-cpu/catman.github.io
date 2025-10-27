package com.example.kotolud.serivce;

import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.mapper.ProductMapper;
import com.example.kotolud.model.Product;
import com.example.kotolud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO create(CreateProductDTO createProductDTO) {

        Product product = productMapper.toEntity(createProductDTO);


        Product savedProduct = productRepository.save(product);


        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт с ID " + id + " не найден"));

        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}