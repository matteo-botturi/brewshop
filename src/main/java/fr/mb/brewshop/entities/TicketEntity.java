package fr.mb.brewshop.entities;

import fr.mb.brewshop.outils.TicketPK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TICKET", schema = "dbo")
public class TicketEntity {
    @EmbeddedId
    private TicketPK id;

    @Column(name = "DATE_VENTE")
    private LocalDate dateVente;

    @Column(name = "HEURE_VENTE")
    private LocalTime heureVente;

    @OneToMany(mappedBy = "ticketEntity")
    private Set<VendreEntity> ventes = new LinkedHashSet<>();

    public TicketEntity(TicketPK id, LocalDate dateVente, LocalTime heureVente) {
        this.id = id;
        this.dateVente = dateVente;
        this.heureVente = heureVente;
    }
}