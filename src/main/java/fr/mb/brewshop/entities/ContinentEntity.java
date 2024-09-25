package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "CONTINENT")
public class ContinentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTINENT", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NOM_CONTINENT", unique = true, nullable = false, length = 25)
    private String nomContinent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "continent")
    private List<CountryEntity> countries;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContinentEntity that = (ContinentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}