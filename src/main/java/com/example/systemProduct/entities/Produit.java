package com.example.systemProduct.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "produit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produit {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Le libelle du produit est obligatoire")
    private String nomProduit;

    @Column(nullable = false)
    @NotBlank(message = "Le prix du produit est obligatoire")
    private double prixProduit;

    @Column(nullable = false)
    @NotBlank(message = "La date d'expiration du produit est obligatoire")
    private LocalDate dateExpiration;
}
