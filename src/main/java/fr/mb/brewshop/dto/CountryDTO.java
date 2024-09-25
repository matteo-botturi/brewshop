package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.CountryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CountryDTO {
    @JsonProperty(index = 1)
    private Integer id;
    @JsonProperty(index = 2)
    private String nom;
    @JsonProperty(index = 3)
    private ContinentDTO continent;

    public CountryDTO(CountryEntity countryEntity) {
        this.id = countryEntity.getId();
        this.nom = countryEntity.getNomPays();
        this.continent = (countryEntity.getContinent() != null) ? new ContinentDTO(countryEntity.getContinent()) : null;
    }

    public static List<CountryDTO> toDTOList(List<CountryEntity> countryEntities){
        return countryEntities.stream().map(CountryDTO::new).collect(Collectors.toList());
    }
}