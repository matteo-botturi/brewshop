package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.ArticleEntity;
import fr.mb.brewshop.entities.MarqueEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;
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
    private String nom;

    @JsonProperty(index = 3)
    private PaysDTO pays;

    @JsonProperty(index = 4)
    private FabricantDTO fabricant;

    //@JsonProperty(index = 5)
    //private List<ArticleDTO> listArticles;
    /*
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 4)
    private URI uri;
    */



    public MarqueDTO(MarqueEntity marqueEntity, boolean includeArticles/*, URI baseUri*/) {
        this.id = marqueEntity.getId();
        this.nom = marqueEntity.getNomMarque();
        this.pays = new PaysDTO(marqueEntity.getPays());
        this.fabricant = new FabricantDTO(marqueEntity.getFabricant());
        //this.articles = includeArticles ? createListArticles(marqueEntity.getArticles()) : new ArrayList<>();
        //this.uri = (baseUri != null) ? baseUri.resolve("continents/" + id) : null;
    }
    /*
    private List<ArticleDTO> createListArticles(Set<ArticleEntity> articleEntities) {
        return articleEntities.stream()
                .map(article -> new ArticleDTO(article.getId(), article.getNomArticle()))
                .collect(Collectors.toList());
    }
    */

    public static List<MarqueDTO> toDTOList(List<MarqueEntity> marqueEntities, boolean includeArticles) {
        return marqueEntities.stream()
                .map(marque -> new MarqueDTO(marque, includeArticles))
                .collect(Collectors.toList());
    }
}