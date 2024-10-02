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
@Table(name = "PAYS", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_PAYS", columnNames = {"NOM_PAYS"})
})
public class PaysEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAYS", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "NOM_PAYS", nullable = false, length = 40)
    private String nomPays;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CONTINENT", nullable = false)
    private ContinentEntity continent;

    @OneToMany(mappedBy = "pays")
    private List<MarqueEntity> marques;

    //Only for DataInitializer
    public PaysEntity(String nomPays, ContinentEntity continent) {
        this.nomPays = nomPays;
        this.continent = continent;
    }
}