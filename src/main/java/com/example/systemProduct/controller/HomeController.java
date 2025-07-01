package com.example.systemProduct.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titrePage","Acceuil");
        return "index";
    }
}
