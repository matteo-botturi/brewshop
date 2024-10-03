package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.ArticleEntity;
import fr.mb.brewshop.entities.MarqueEntity;
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
public class MarqueDTO {

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @JsonProperty(index = 2)
    private String nomMarque;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 3)
    private String nomPays;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private String nomFabricant;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 5)
    private List<ArticleDTO> articles;
    /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
    */
    public MarqueDTO(MarqueEntity marqueEntity, boolean includeArticles/*, URI baseUri*/) {
        this.id = marqueEntity.getId();
        this.nomMarque = marqueEntity.getNomMarque();
        this.nomPays = marqueEntity.getPays() != null ? marqueEntity.getPays().getNomPays() : null;
        this.nomFabricant = marqueEntity.getFabricant() != null ? marqueEntity.getFabricant().getNomFabricant() : null;
        this.articles = includeArticles ? createListArticles(marqueEntity.getArticles()) : null;
        //this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }

    private List<ArticleDTO> createListArticles(List<ArticleEntity> articleEntities) {
        return articleEntities.stream()
                .map(article -> new ArticleDTO(article, false))
                .collect(Collectors.toList());
    }

    public static List<MarqueDTO> toDTOList(List<MarqueEntity> marqueEntities) {
        return marqueEntities.stream()
                .sorted(Comparator.comparing(MarqueEntity::getId))
                .map(marque -> new MarqueDTO(marque, false))
                .collect(Collectors.toList());
    }
}