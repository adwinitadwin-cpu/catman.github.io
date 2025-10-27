package com.example.kotolud.controller.admin;

import com.example.kotolud.serivce.ProductService;
import com.example.kotolud.serivce.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboard {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String adminDashboard(Model model) {
        Long countProduct = productService.count();
        model.addAttribute("count", countProduct);


        Long countCategory = categoryService.count();
        model.addAttribute("countCategory", countCategory);

        return "admin/dashboard";
    }

}
