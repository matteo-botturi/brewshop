package fr.mb.brewshop.entities;

import fr.mb.brewshop.outils.TicketPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "TICKET")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    @EmbeddedId
    TicketPK ticketPk;
    @Column(name = "DATE_VENTE")
    LocalDate dateVente;
    @Column(name = "HEURE_VENTE")
    LocalTime heureVente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketEntity that = (TicketEntity) o;
        return Objects.equals(ticketPk, that.ticketPk);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketPk);
    }
}

