package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COULEUR", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_COULEUR", columnNames = {"NOM_COULEUR"})
})
public class CouleurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COULEUR", nullable = false)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @Column(name = "NOM_COULEUR", nullable = false, length = 25)
    private String nomCouleur;

    @OneToMany(mappedBy = "couleur")
    private Set<ArticleEntity> articles = new LinkedHashSet<>();

    public CouleurEntity(String nomCouleur) {
        this.nomCouleur = nomCouleur;
    }
}