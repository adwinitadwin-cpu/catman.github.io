package com.example.kotolud.controller.admin;

import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.dto.product.CreateProductDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.dto.product.UpdateProductDTO;
import com.example.kotolud.model.Product;
import com.example.kotolud.serivce.ProductService;
import com.example.kotolud.serivce.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/admin-product")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping
    public String listProducts(Model model) {

        List<ProductResponseDTO > listProduct = productService.getAllProducts();
        model.addAttribute("products", listProduct);
        return "admin/product/list";
    }


    /**
     * Форма создания нового продукта
     */
    @GetMapping("/form")
    public String form(Model model) {
        // Создаём пустой DTO для формы
        model.addAttribute("product", new CreateProductDTO());

        // Получаем все категории и передаём под именем "categories"
        List<CategoryResponseDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "admin/product/form";
    }


    /**
     * Сохранение нового продукта
     */
    @PostMapping("/save")
    public String saveProduct(@Valid CreateProductDTO product,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // Проверка валидации
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors: {}", bindingResult.getAllErrors());

            model.addAttribute("product", product);
            // ВАЖНО: передаём категории при ошибке валидации
            model.addAttribute("categories", categoryService.findAll());

            return "admin/product/form";
        }

        try {
            productService.create(product);
            redirectAttributes.addFlashAttribute("success", "Продукт успешно создан");
            log.info("✅ Product created successfully");

            return "redirect:/admin-product";

        } catch (Exception e) {
            log.error("❌ Error creating product: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());

            return "redirect:/admin-product/form";
        }
    }

    @GetMapping("/view/{id}")
    public String viewProduct(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id));
        return "admin/product/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("success", "Продукт успешно удален");
            return "redirect:/admin-product";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());
            return "redirect:/admin-product";
        }
    }


    /**
     * Форма редактирования продукта
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        try {
            ProductResponseDTO product = productService.getProductById(id);
            model.addAttribute("product", product);

            List<CategoryResponseDTO> categories = categoryService.findAll();
            model.addAttribute("categories", categories);

            log.info("✅ Edit form opened for product: {}", id);

            return "admin/product/update";

        } catch (RuntimeException e) {
            log.error("❌ Product not found: {}", id, e);
            return "redirect:/admin-product";
        }
    }

    /**
     * Обновление продукта
     */
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid UpdateProductDTO product,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // Проверка валидации
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors: {}", bindingResult.getAllErrors());

            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAll());

            return "admin/product/update";
        }

        try {
            productService.update(id, product);
            redirectAttributes.addFlashAttribute("success", "Продукт успешно обновлен");
            log.info("✅ Product updated successfully: {}", id);

            return "redirect:/admin-product/view/" + id;

        } catch (RuntimeException e) {
            log.error("❌ Error updating product: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());

            return "redirect:/admin-product/edit/" + id;
        }
    }

    @PostMapping("/upload-image/{id}")
    public String uploadImage(@PathVariable Long id,
                              @RequestParam MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.uploadPicture(id, file);
            redirectAttributes.addFlashAttribute("success", "Изображение загружено");
            return "redirect:/admin-product/view/" + id;
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка загрузки");
            return "redirect:/admin-product/view/" + id;
        }
    }

    /**
     * Удаляет изображение продукта
     */
    @PostMapping("/delete-image/{id}")
    public String deleteImage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            log.info("Deleting image for product: {}", id);

            ProductResponseDTO product = productService.deletePicture(id);

            redirectAttributes.addFlashAttribute("success", "Изображение успешно удалено");
            log.info("✅ Image deleted successfully for product: {}", id);

            return "redirect:/admin-product/view/" + id;

        } catch (IOException e) {
            log.error("❌ Error deleting image for product {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении изображения: " + e.getMessage());
            return "redirect:/admin-product/view/" + id;

        } catch (RuntimeException e) {
            log.error("❌ Product not found: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Продукт не найден");
            return "redirect:/admin-product";

        } catch (Exception e) {
            log.error("❌ Unexpected error deleting image: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Неожиданная ошибка");
            return "redirect:/admin-product/view/" + id;
        }
    }
}
