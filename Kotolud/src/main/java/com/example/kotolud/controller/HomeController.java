package com.example.kotolud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {


    @GetMapping
    public String HomeController() {
        return "index";

    }
    @GetMapping("/product")
    public String ProductController() {
        return "pricing";
    }
}
