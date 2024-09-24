package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "COULEUR")
public class CouleurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COULEUR", nullable = false)
    private Integer id;

    @Column(name = "NOM_COULEUR", unique = true, nullable = false, length = 25)
    private String nomCouleur;
}