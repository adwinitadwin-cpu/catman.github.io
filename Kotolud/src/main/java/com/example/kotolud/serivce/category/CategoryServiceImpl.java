package com.example.kotolud.serivce.category;

import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.dto.category.CreateCategoryDTO;
import com.example.kotolud.mapper.CategoryMapper;
import com.example.kotolud.model.Category;
import com.example.kotolud.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO create(CreateCategoryDTO createCategoryDTO) {
        Category category = categoryMapper.toEntity(createCategoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория с ID " + id + " не найдена"));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }

    /**
     * Обновить категорию
     */
    @Override
    public CategoryResponseDTO update(Long id, CreateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория с ID " + id + " не найдена"));

        categoryMapper.updateEntity(updateCategoryDTO, category);
        category = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(category);
    }

    /**
     * Удалить категорию
     */
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория с ID " + id + " не найдена"));

        categoryRepository.delete(category);
    }
}