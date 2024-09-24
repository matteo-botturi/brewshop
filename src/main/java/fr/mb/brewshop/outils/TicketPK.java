package fr.mb.brewshop.outils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
}