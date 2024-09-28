package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.MarqueEntity;
import fr.mb.brewshop.entities.PaysEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PaysDTO {

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @JsonProperty(index = 2)
    private String nom;

    @NotNull
    @JsonProperty(index = 3)
    private ContinentDTO continent;

    @JsonProperty(index = 4)
    private List<PaysDTOMarques> marques;
    /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
    */

    public PaysDTO(PaysEntity paysEntity) {
        this(paysEntity, true/*, null*/);
    }

    public PaysDTO(PaysEntity paysEntity, boolean includeMarques/*, URI baseUri*/){
        this.id = paysEntity.getId();
        this.nom = paysEntity.getNomPays();
        this.continent = (paysEntity.getContinent() != null) ? new ContinentDTO(paysEntity.getContinent()) : null;
        this.marques = createListMarques(paysEntity.getMarques(), includeMarques);
        //this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }

    private List<PaysDTOMarques> createListMarques(Set<MarqueEntity> marqueEntities, boolean includeMarques) {
        if (includeMarques && marqueEntities != null)
            return marqueEntities.stream()
                    .map(marque -> new PaysDTOMarques(marque.getId(), marque.getNomMarque()))
                    .collect(Collectors.toList());
        else
            return new ArrayList<>();
    }

    public static List<PaysDTO> toDTOList(List<PaysEntity> paysEntities){
        return paysEntities.stream().map(PaysDTO::new).collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    class PaysDTOMarques {
        private Integer id;
        private String nom;
    }
}