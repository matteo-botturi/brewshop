package fr.mb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.entities.CouleurEntity;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CouleurDTO {
    @JsonProperty(index = 1)
    private Integer id;

    @JsonProperty(index = 2)
    private String nom;

    public CouleurDTO(CouleurEntity couleurEntity){
        id = couleurEntity.getId();
        nom = couleurEntity.getNom();
    }

    public static List<CouleurDTO> toDTOList(List<CouleurEntity> couleurEntities){
        List<CouleurDTO> couleurDTOList = new ArrayList<>();
        for (CouleurEntity couleurEntity : couleurEntities)
            couleurDTOList.add(new CouleurDTO(couleurEntity));
        return couleurDTOList;
    }
}