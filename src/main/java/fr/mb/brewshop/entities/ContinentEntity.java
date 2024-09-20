package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "CONTINENT", schema = "dbo", catalog = "brewshop")
public class ContinentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_CONTINENT")
    private Integer id;

    @Basic
    @Column(name = "NOM_CONTINENT")
    private String nom;

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