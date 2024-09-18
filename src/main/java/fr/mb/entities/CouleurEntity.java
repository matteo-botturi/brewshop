package fr.mb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "COULEUR")
@Getter
@Setter
public class CouleurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COULEUR")
    private Integer id;

    @Column(name = "NOM_COULEUR")
    private String nom;
}