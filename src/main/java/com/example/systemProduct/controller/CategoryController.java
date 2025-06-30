package com.example.systemProduct.controller;

import com.example.systemProduct.entities.Category;
import com.example.systemProduct.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("category", new Category());
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
            categoryService.addCategory(category);
            ra.addFlashAttribute("msg", "La catégorie a été créée avec succès!");
            return "redirect:/category";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleCategorie", null, e.getMessage());
            return "category/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée"));
        model.addAttribute("category", category);
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
            categoryService.updateCategory(id, category);
            ra.addFlashAttribute("msg", "La catégorie a été mise à jour avec succès!");
            return "redirect:/category";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelleCategorie", null, e.getMessage());
            return "category/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        categoryService.deleteCategory(id);
        ra.addFlashAttribute("msg", "La catégorie a été supprimée avec succès!");
        return "redirect:/category";
    }
}
