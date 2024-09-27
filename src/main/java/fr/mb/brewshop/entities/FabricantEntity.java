package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "FABRICANT", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_FABRICANT", columnNames = {"NOM_FABRICANT"})
})
public class FabricantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FABRICANT", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "NOM_FABRICANT", nullable = false, length = 40)
    private String nomFabricant;

    @OneToMany(mappedBy = "fabricant")
    private Set<MarqueEntity> marques = new LinkedHashSet<>();

}