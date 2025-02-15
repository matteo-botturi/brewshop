package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.PaysEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ContinentDTO {

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @JsonProperty(index = 2)
    private String nomContinent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 3)
    private List<ContinentDTOPays> listPays;
     /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
     */

    public ContinentDTO(ContinentEntity continentEntity) {
        this(continentEntity, false/*, null*/);
    }

    public ContinentDTO(ContinentEntity continentEntity, boolean includePays/*, URI baseUri*/) {
        this.id = continentEntity.getId();
        this.nomContinent = continentEntity.getNomContinent();
        this.listPays = createListPays(continentEntity.getListPays(), includePays);
        //this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }

    private List<ContinentDTOPays> createListPays(List<PaysEntity> listPays, boolean includePays) {
        if (includePays && listPays != null)
            return listPays.stream()
                    .sorted(Comparator.comparing(PaysEntity::getId))
                    .map(country -> new ContinentDTOPays(country.getId(), country.getNomPays()))
                    .collect(Collectors.toList());
        return null;
    }

    public static List<ContinentDTO> toDtoList(List<ContinentEntity> continentEntities) {
        return continentEntities.stream()
                .sorted(Comparator.comparing(ContinentEntity::getId))
                .map(continent -> new ContinentDTO(continent, false))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    class ContinentDTOPays {
        private final Integer id;
        private final String nom;
    }
}