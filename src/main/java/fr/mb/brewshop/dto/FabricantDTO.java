package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.FabricantEntity;
import fr.mb.brewshop.entities.MarqueEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FabricantDTO {

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @JsonProperty(index = 2)
    private String nomFabricant;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 3)
    private List<FabricantDTOMarques> marques;
    /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
     */

    public FabricantDTO(FabricantEntity fabricantEntity) {
        this(fabricantEntity, false/*, null*/);
    }

    public FabricantDTO(FabricantEntity fabricantEntity, boolean includeMarques/*, URI baseUri*/){
        this.id = fabricantEntity.getId();
        this.nomFabricant = fabricantEntity.getNomFabricant();
        this.marques = createListMarques(fabricantEntity.getMarques(), includeMarques);
        //this.uri = (baseUri != null) ? baseUri.resolve("fabricants/" + id) : null;
    }

    private List<FabricantDTOMarques> createListMarques(List<MarqueEntity> marques, boolean includeMarques) {
        if (includeMarques && marques != null)
            return marques.stream()
                    .map(country -> new FabricantDTOMarques(country.getId(), country.getNomMarque()))
                    .collect(Collectors.toList());
        return null;
    }

    public static List<FabricantDTO> toDtoList(List<FabricantEntity> fabricantEntities) {
        return fabricantEntities.stream()
                .sorted(Comparator.comparing(FabricantEntity::getId))
                .map(fabricant -> new FabricantDTO(fabricant, false))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    class FabricantDTOMarques {
        private final Integer id;
        private final String nom;
    }
}