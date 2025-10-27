package com.example.kotolud.controller.admin;

import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.dto.category.CreateCategoryDTO;
import com.example.kotolud.serivce.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin-category")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategory(Model model) {
        List<CategoryResponseDTO> list = categoryService.findAll();
        model.addAttribute("categoryList", list);
        return "admin/category/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("category", new CreateCategoryDTO());
        return "admin/category/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        try {
            CategoryResponseDTO category = categoryService.findById(id);
            model.addAttribute("category", category);
            model.addAttribute("isEdit", true);
            return "admin/category/form";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Категория не найдена");
            return "redirect:/admin-category";
        }
    }

    @PostMapping("/save")
    public String save(@Valid CreateCategoryDTO createCategoryDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        // Проверка валидации
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", createCategoryDTO);
            return "admin/category/form";
        }

        try {
            categoryService.create(createCategoryDTO);
            redirectAttributes.addFlashAttribute("success", "Категория успешно создана");
            return "redirect:/admin-category";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании категории: " + e.getMessage());
            return "redirect:/admin-category/form";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid CreateCategoryDTO createCategoryDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        // Проверка валидации
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", createCategoryDTO);
            model.addAttribute("isEdit", true);
            return "admin/category/form";
        }

        try {
            categoryService.update(id, createCategoryDTO);
            redirectAttributes.addFlashAttribute("success", "Категория успешно обновлена");
            return "redirect:/admin-category";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении категории: " + e.getMessage());
            return "redirect:/admin-category/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Категория успешно удалена");
            return "redirect:/admin-category";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении категории: " + e.getMessage());
            return "redirect:/admin-category";
        }
    }
}