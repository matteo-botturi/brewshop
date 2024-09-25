package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.TicketEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {
    @JsonProperty(index = 1)
    private Integer annee;
    @JsonProperty(index = 2)
    private Integer numeroTicket;
    @JsonProperty(index = 3)
    private String dateVente;
    @JsonProperty(index = 4)
    private String heureVente;

    public TicketDTO(TicketEntity ticketEntity) {
        this.annee = ticketEntity.getTicketPk().getAnnee();
        this.numeroTicket = ticketEntity.getTicketPk().getNumeroTicket();
        this.dateVente = (ticketEntity.getDateVente() != null) ? ticketEntity.getDateVente().toString() : null;
        this.heureVente = (ticketEntity.getHeureVente() != null) ? ticketEntity.getHeureVente().toString() : null;
    }

    public static List<TicketDTO> toDTOList(List<TicketEntity> ticketEntities) {
        return ticketEntities.stream().map(TicketDTO::new).collect(Collectors.toList());
    }
}

