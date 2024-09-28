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
@Table(name = "MARQUE", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_MARQUE", columnNames = {"NOM_MARQUE"})
})
public class MarqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MARQUE", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "NOM_MARQUE", nullable = false, length = 40)
    private String nomMarque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAYS")
    private PaysEntity pays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FABRICANT")
    private FabricantEntity fabricant;

    @OneToMany(mappedBy = "marque")
    private Set<ArticleEntity> articles = new LinkedHashSet<>();

    public MarqueEntity(String nomMarque, PaysEntity pays, FabricantEntity fabricant) {
        this.nomMarque = nomMarque;
        this.pays = pays;
        this.fabricant = fabricant;
    }
}