package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.CountryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ContinentDTO {
    @JsonProperty(index = 1)
    private Integer id;
    @JsonProperty(index = 2)
    private String nom;
    @JsonProperty(index = 3)
    private List<ContinentDTOCountries> countries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;

    public ContinentDTO(ContinentEntity continentEntity) {
        this(continentEntity, true, null);
    }

    public ContinentDTO(ContinentEntity continentEntity, boolean includeCountries, URI baseUri) {
        this.id = continentEntity.getId();
        this.nom = continentEntity.getNomContinent();
        this.countries = createCountriesList(continentEntity.getCountries(), includeCountries);
        this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }

    private List<ContinentDTOCountries> createCountriesList(List<CountryEntity> countriesList, boolean includeCountries) {
        if (includeCountries && countriesList != null)
            return countriesList.stream()
                    .map(country -> new ContinentDTOCountries(country.getId(), country.getNomPays()))
                    .collect(Collectors.toList());
        else
            return new ArrayList<>();
    }

    public static List<ContinentDTO> toDtoList(List<ContinentEntity> continentEntities) {
        return continentEntities.stream().map(ContinentDTO::new).collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    class ContinentDTOCountries {
        private final Integer id;
        private final String nom;
    }
}