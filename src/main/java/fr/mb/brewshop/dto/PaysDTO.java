package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.entities.MarqueEntity;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.views.Views;
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
public class PaysDTO {

    @JsonView(Views.Internal.class)
    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @JsonProperty(index = 2)
    private String nomPays;

    @NotNull
    @JsonProperty(index = 3)
    private String nomContinent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private List<PaysDTOMarques> marques;
    /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
    */

    public PaysDTO(PaysEntity paysEntity) {
        this(paysEntity, false/*, null*/);
    }

    public PaysDTO(PaysEntity paysEntity, boolean includeMarques/*, URI baseUri*/){
        this.id = paysEntity.getId();
        this.nomPays = paysEntity.getNomPays();
        this.nomContinent = paysEntity.getContinent().getNomContinent();
        this.marques = createListMarques(paysEntity.getMarques(), includeMarques);
        //this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }


    private List<PaysDTOMarques> createListMarques(List<MarqueEntity> marqueEntities, boolean includeMarques) {
        if (includeMarques && marqueEntities != null)
            return marqueEntities.stream()
                    .map(marque -> new PaysDTOMarques(marque.getId(), marque.getNomMarque()))
                    .collect(Collectors.toList());
        return null;
    }

    public static List<PaysDTO> toDTOList(List<PaysEntity> paysEntities) {
        return paysEntities.stream()
                .sorted(Comparator.comparing(PaysEntity::getId))
                .map(pays -> new PaysDTO(pays, false))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    class PaysDTOMarques {
        private Integer id;
        private String nom;
    }
}