package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.CouleurEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CouleurDTO {
    @JsonProperty(index = 1)
    private Integer id;

    @JsonProperty(index = 2)
    private String nom;

    public CouleurDTO(CouleurEntity couleurEntity){
        id = couleurEntity.getId();
        nom = couleurEntity.getNomCouleur();
    }

    public static List<CouleurDTO> toDTOList(List<CouleurEntity> couleurEntities) {
        return couleurEntities.stream().map(CouleurDTO::new).collect(Collectors.toList());
    }
}