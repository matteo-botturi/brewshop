package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.PaysEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PaysDTO {
    @JsonProperty(index = 1)
    private Integer id;
    @JsonProperty(index = 2)
    private String nom;
    @JsonProperty(index = 3)
    private ContinentDTO continent;

    public PaysDTO(PaysEntity paysEntity) {
        this.id = paysEntity.getId();
        this.nom = paysEntity.getNomPays();
        this.continent = (paysEntity.getContinent() != null) ? new ContinentDTO(paysEntity.getContinent()) : null;
    }

    public static List<PaysDTO> toDTOList(List<PaysEntity> countryEntities){
        return countryEntities.stream().map(PaysDTO::new).collect(Collectors.toList());
    }
}