package com.example.systemProduct.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.example.systemProduct.dto.ProduitDTO;
import com.example.systemProduct.entities.Produit;
import com.example.systemProduct.repository.CategorieRepository;
import com.example.systemProduct.repository.ProduitRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProduitService {
    
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public Produit addProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream().map(this::maProduitDTO).collect(Collectors.toList());
    }

    public Produit getProduitByID(UUID id) {
        return produitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Le produit " + id + " n'existe pas"));
    }

    public ProduitDTO getProduitDTOByID(UUID id) {
        return produitRepository.findById(id).map(this::maProduitDTO).orElseThrow(() -> new IllegalArgumentException("Le produit " + id + " n'existe pas"));
    }

    public Produit updateProduit(UUID id, Produit produit) {
        return produitRepository.findById(id).map(existingProduit -> {
            existingProduit.setNomProduit(produit.getNomProduit());
            existingProduit.setPrixProduit(produit.getPrixProduit());
            existingProduit.setDateExpiration(produit.getDateExpiration());
            existingProduit.setCategorie(produit.getCategorie());
            return produitRepository.save(existingProduit);
        }).orElseThrow(() -> new IllegalArgumentException("Le produit " + id + " n'existe pas"));
    }

    public void deleteProduit(UUID id) {
        if(!produitRepository.existsById(id)) {
            throw new EntityNotFoundException("Le produit " + id + " n'existe pas");
        }
        produitRepository.deleteById(id);
    }

    public ProduitDTO maProduitDTO(Produit produit) {
        return new ProduitDTO(
            produit.getId(), 
            produit.getNomProduit(), 
            produit.getPrixProduit(), 
            produit.getDateExpiration(), 
            produit.getCategorie().getLibelleCategorie()
        );
    }
}
