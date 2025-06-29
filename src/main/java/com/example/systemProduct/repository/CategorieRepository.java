package com.example.systemProduct.repository;

import com.example.systemProduct.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Categorie, UUID> {
    boolean existsDistinctByLibelleCategorie(String libelleCategorie);
}
