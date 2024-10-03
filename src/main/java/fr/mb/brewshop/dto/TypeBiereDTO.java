package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.entities.TypeBiereEntity;
import fr.mb.brewshop.views.Views;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TypeBiereDTO {

    @JsonView(Views.Internal.class)
    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @JsonView(Views.Public.class)
    @JsonProperty(index = 2)
    private String nomTypeBiere;

    public TypeBiereDTO(TypeBiereEntity typeBiereEntity){
        this.id = typeBiereEntity.getId();
        this.nomTypeBiere = typeBiereEntity.getNomTypeBiere();
    }

    public static List<TypeBiereDTO> toDTOList(List<TypeBiereEntity> typeBiereEntities) {
        return typeBiereEntities.stream()
                .sorted(Comparator.comparing(TypeBiereEntity::getId))
                .map(TypeBiereDTO::new)
                .collect(Collectors.toList());
    }
}