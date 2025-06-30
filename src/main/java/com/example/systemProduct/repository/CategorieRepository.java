package com.example.systemProduct.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.systemProduct.entities.Category;

import java.util.UUID;

public interface CategorieRepository extends JpaRepository<Category, UUID> {
    boolean existsDistinctByLibelleCategorie(String libelleCategorie);
}
