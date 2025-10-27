package com.example.kotolud.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProductDTO {

    @NotNull(message = "ID продукта обязателен")
    private Long id;

    @NotBlank(message = "Название продукта не может быть пустым")
    @Size(min = 2, max = 255, message = "Название должно быть от 2 до 255 символов")
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @Size(max = 500, message = "URL продукта не должен превышать 500 символов")
    private String urlProduct;

    private Long categoryId;
}