package com.example.kotolud.controller;

import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.serivce.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public String product(@PathVariable Long id, Model model) {
        ProductResponseDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/view";
    }


    @GetMapping
    public String product(Model model) {

        List<ProductResponseDTO> list = productService.getAllProducts();
        model.addAttribute("products", list);
        return "product/list";
    }













}
