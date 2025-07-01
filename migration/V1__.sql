CREATE TABLE categories
(
    id                BINARY(16)   NOT NULL,
    libelle_categorie VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE produits
(
    id              BINARY(16)   NOT NULL,
    nom_produit     VARCHAR(255) NOT NULL,
    prix_produit    DOUBLE       NOT NULL,
    date_expiration date         NOT NULL,
    categorie_id    BINARY(16)   NULL,
    CONSTRAINT pk_produits PRIMARY KEY (id)
);

ALTER TABLE produits
    ADD CONSTRAINT FK_PRODUITS_ON_CATEGORIE FOREIGN KEY (categorie_id) REFERENCES categories (id);