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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAYS", nullable = false)
    private Integer id;

    @Column(name = "NOM_PAYS", unique = true, nullable = false, length = 40)
    private String nomPays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTINENT", nullable = false)
    private ContinentEntity continent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}