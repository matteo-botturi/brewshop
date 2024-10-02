package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.TypeBiereEntity;
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

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @JsonProperty(index = 2)
    private String nom;

    public TypeBiereDTO(TypeBiereEntity typeBiereEntity){
        this.id = typeBiereEntity.getId();
        this.nom = typeBiereEntity.getNomType();
    }

    public static List<TypeBiereDTO> toDTOList(List<TypeBiereEntity> typeBiereEntities) {
        return typeBiereEntities.stream()
                .sorted(Comparator.comparing(TypeBiereEntity::getId))
                .map(TypeBiereDTO::new)
                .collect(Collectors.toList());
    }
}