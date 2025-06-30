package com.example.systemProduct.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "produits") // Évite les références circulaires dans toString()
public class Categorie {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "le libelle de la categorie est obligatoire")
    private String libelleCategorie;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Important avec Lombok @Builder
    private List<Produit> produits = new ArrayList<>();

    // Méthodes utilitaires pour gérer la relation bidirectionnelle
    public void addProduit(Produit produit) {
        produits.add(produit);
        produit.setCategorie(this);
    }

    public void removeProduit(Produit produit) {
        produits.remove(produit);
        produit.setCategorie(null);
    }
}