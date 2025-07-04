package com.example.systemProduct.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.systemProduct.entities.Category;
import com.example.systemProduct.repository.CategorieRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategorieRepository categorieRepository;

    public Category addCategorie(Category categorie) {
        if(categorieRepository.existsDistinctByLibelleCategorie(categorie.getLibelleCategorie())) {
            throw new IllegalArgumentException("La categorie " + categorie.getLibelleCategorie() + " existe deja");
        }
        return categorieRepository.save(categorie);
    }

    public List<Category> getAllCategories(){
        return categorieRepository.findAll();
    }

    public Optional<Category> getCategorieByID(UUID id){
        return categorieRepository.findById(id);    
    }

    public Category updateCategorie(UUID id, Category categorie){
        return categorieRepository.findById(id).map(existingCategorie -> {
            existingCategorie.setLibelleCategorie(categorie.getLibelleCategorie());
            return categorieRepository.save(existingCategorie);
        }).orElseThrow(()-> new IllegalArgumentException("La categorie " + id + " n'existe pas"));
    }

    public void deleteCategorie(UUID id){
        if (!categorieRepository.existsById(id)) {
            throw new EntityNotFoundException("La categorie " + id + " n'existe pas");
        }
        categorieRepository.deleteById(id);
    }
}
