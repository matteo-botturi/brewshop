package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.VendreEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class VendreDTO {

    @JsonProperty(index = 1)
    private Integer annee;

    @JsonProperty(index = 2)
    private Integer numeroTicket;

    @JsonProperty(index = 3)
    private Integer idArticle;

    @JsonProperty(index = 4)
    private Integer quantite;

    @JsonProperty(index = 5)
    private BigDecimal prixVente;

    public VendreDTO(VendreEntity vendreEntity) {
        this.annee = vendreEntity.getId().getAnnee();
        this.numeroTicket = vendreEntity.getId().getNumeroTicket();
        this.idArticle = vendreEntity.getId().getIdArticle();
        this.quantite = vendreEntity.getQuantite();
        this.prixVente = vendreEntity.getPrixVente();
    }

    public static List<VendreDTO> toDTOList(List<VendreEntity> vendreEntities) {
        return vendreEntities.stream()
                .map(VendreDTO::new)
                .collect(Collectors.toList());
    }
}