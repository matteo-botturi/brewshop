package fr.mb.brewshop.outils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class VendrePK implements Serializable {

    @NotNull
    @Column(name = "ANNEE", nullable = false)
    private Integer annee;

    @NotNull
    @Column(name = "NUMERO_TICKET", nullable = false)
    private Integer numeroTicket;

    @NotNull
    @Column(name = "ID_ARTICLE", nullable = false)
    private Integer idArticle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VendrePK entity = (VendrePK) o;
        return Objects.equals(this.idArticle, entity.idArticle) &&
                Objects.equals(this.annee, entity.annee) &&
                Objects.equals(this.numeroTicket, entity.numeroTicket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArticle, annee, numeroTicket);
    }
}