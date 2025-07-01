package com.example.systemProduct.controller;

import com.example.systemProduct.entities.Produit;
import com.example.systemProduct.service.CategoryService;
import com.example.systemProduct.service.ProduitService;
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
@RequestMapping("/produit")
public class ProduitController {

    private final ProduitService produitService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllProduits(Model model) {
        model.addAttribute("produits", produitService.getAllProduits());
        model.addAttribute("currentPage", "produit");

        return "produit/list";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", "produit.new");
        return "produit/add";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("produit") Produit produit,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "produit/add";
        }
        try {
            produitService.addProduit(produit);
            ra.addFlashAttribute("msg", "Le produit a été créé avec succès!");
            return "redirect:/produit";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("nomProduit", null, e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "produit/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        Produit produit = produitService.getProduitByID(id);
        model.addAttribute("produit", produit);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", "produit.edit");

        return "produit/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable UUID id,
                         @Valid @ModelAttribute("produit") Produit produit,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "produit/edit";
        }
        try {
            produitService.updateProduit(id, produit);
            ra.addFlashAttribute("msg", "Le produit a été mis à jour avec succès!");
            return "redirect:/produit";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("nomProduit", null, e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "produit/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, RedirectAttributes ra) {
        produitService.deleteProduit(id);
        ra.addFlashAttribute("msg", "Le produit a été supprimé avec succès!");
        return "redirect:/produit";
    }
}
