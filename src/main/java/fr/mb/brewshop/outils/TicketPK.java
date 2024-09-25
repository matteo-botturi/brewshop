package fr.mb.brewshop.outils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TicketPK implements Serializable {
    @Column(name = "ANNEE")
    private Integer annee;
    @Column(name = "NUMERO_TICKET")
    private Integer numeroTicket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketPK ticketPK = (TicketPK) o;
        return Objects.equals(annee, ticketPK.annee) && Objects.equals(numeroTicket, ticketPK.numeroTicket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annee, numeroTicket);
    }
}