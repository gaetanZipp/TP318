package com.example.systemProduct.repository;

import com.example.systemProduct.entities.Categorie;
import com.example.systemProduct.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, UUID> {
    List<Produit> findByCategorie_Id(Categorie categorie);
    boolean existsDistinctByDateExpiration(LocalDate localDate);
}
