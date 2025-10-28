package com.example.kotolud.controller;

import com.example.kotolud.dto.category.CategoryResponseDTO;
import com.example.kotolud.dto.product.ProductResponseDTO;
import com.example.kotolud.serivce.ProductService;
import com.example.kotolud.serivce.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;



    @GetMapping
    public String HomeController(Model model) {


        List<ProductResponseDTO> getLastProduct = productService.getLimitProduct(5);
        model.addAttribute("carusel", getLastProduct);

        List<CategoryResponseDTO> categoryResponseDTOList = categoryService.findAll();
        model.addAttribute("categoryResponseDTOList", categoryResponseDTOList);


        return "index";
    }

}
