package fr.mb.brewshop.entities;

import fr.mb.brewshop.outils.TicketPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "Ticket")
@Getter
@Setter
public class TicketEntity {
    @EmbeddedId
    TicketPK ticketPk;
    @Column(name = "DATE_VENTE")
    LocalDate localDate;
    @Column(name = "HEURE_VENTE")
    LocalTime localTime;
}

