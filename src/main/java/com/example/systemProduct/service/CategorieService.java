package com.example.systemProduct.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.systemProduct.entities.Categorie;
import com.example.systemProduct.repository.CategorieRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorieService {
    
    private final CategorieRepository categorieRepository;

    public Categorie addCategorie(Categorie categorie) {
        if(categorieRepository.existsDistinctByLibelleCategorie(categorie.getLibelleCategorie())) {
            throw new IllegalArgumentException("La categorie " + categorie.getLibelleCategorie() + " existe deja");
        }
        return categorieRepository.save(categorie);
    }

    public List<Categorie> getAllCategories(){
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieByID(UUID id){
        return categorieRepository.findById(id);    
    }

    public Categorie updateCategorie(UUID id, Categorie categorie){
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
