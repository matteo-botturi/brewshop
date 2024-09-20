package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "PAYS")
public class CountryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_PAYS")
    private Integer idPays;

    @Basic
    @Column(name = "NOM_PAYS")
    private String nomPays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTINENT")
    private ContinentEntity continent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(idPays, that.idPays);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPays);
    }
}