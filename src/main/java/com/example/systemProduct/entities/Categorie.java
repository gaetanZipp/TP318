package com.example.systemProduct.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categorie {
    @Id
    @GeneratedValue
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "le libelle de la categorie est obligatoire")
    private String libelleCategorie;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Produit> produits;
}
