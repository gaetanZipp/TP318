package com.example.systemProduct.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.systemProduct.entities.Category;
import com.example.systemProduct.service.CategoryService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;



    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", "category");
        return "category/list";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("currentPage", "category.new");

        return "category/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "category/add";
        }
        try {
            categoryService.addCategorie(category);
            ra.addFlashAttribute("msg", "La catégorie a été créée avec succès!");
            return "redirect:/category";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleCategorie", null, e.getMessage());
            return "category/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        Category category = categoryService.getCategorieByID(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée"));
        model.addAttribute("category", category);
        model.addAttribute("currentPage", "category.edit");

        return "category/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable UUID id,
                         @Valid @ModelAttribute("category") Category category,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }
        try {
            categoryService.updateCategorie(id, category);
            ra.addFlashAttribute("msg", "La catégorie a été mise à jour avec succès!");
            return "redirect:/category";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleCategorie", null, e.getMessage());
            return "category/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        categoryService.deleteCategorie(id);
        ra.addFlashAttribute("msg", "La catégorie a été supprimée avec succès!");
        return "redirect:/category";
    }
}
