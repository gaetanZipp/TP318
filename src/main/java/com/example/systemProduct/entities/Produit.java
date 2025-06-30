package com.example.systemProduct.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "produits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "categorie") // Évite les références circulaires dans toString()
public class Produit {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Le libelle du produit est obligatoire")
    private String nomProduit;

    @Column(nullable = false)
    @NotNull(message = "Le prix du produit est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prixProduit;

    @Column(nullable = false)
    @NotNull(message = "La date d'expiration du produit est obligatoire")
    private LocalDate dateExpiration;

    // Ajoutez cette propriété pour la relation
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}