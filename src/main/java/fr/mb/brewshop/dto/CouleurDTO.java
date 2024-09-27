package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.entities.CouleurEntity;
import fr.mb.brewshop.views.Views;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CouleurDTO {
    @JsonView(Views.Internal.class)
    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @JsonView(Views.Public.class)
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