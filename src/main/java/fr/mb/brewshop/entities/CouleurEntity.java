package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "COULEUR")
public class CouleurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COULEUR", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NOM_COULEUR", unique = true, nullable = false)
    private String nom;
}