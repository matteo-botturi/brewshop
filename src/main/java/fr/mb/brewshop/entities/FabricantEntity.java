package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private List<MarqueEntity> marques;

    //Only for DataInitializer
    public FabricantEntity(String nomFabricant) {
        this.nomFabricant = nomFabricant;
    }
}