package com.example.systemProduct.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProduitDTO(
    UUID id, 
    String nomProduit, 
    Double prixProduit, 
    LocalDate dateExpiration, 
    String categorieNom) 
{}
