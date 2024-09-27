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
@Table(name = "CONTINENT", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_CONTINENT", columnNames = {"NOM_CONTINENT"})
})
public class ContinentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTINENT", nullable = false)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @Column(name = "NOM_CONTINENT", nullable = false, length = 25)
    private String nomContinent;

    @OneToMany(mappedBy = "continent")
    private Set<PaysEntity> listPays = new LinkedHashSet<>();

    public ContinentEntity(String nomContinent) {
        this.nomContinent = nomContinent;
    }
}