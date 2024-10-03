package fr.mb.brewshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mb.brewshop.entities.ArticleEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {

    @JsonProperty(index = 1)
    private Integer id;

    @Size(max = 60)
    @NotNull
    @JsonProperty(index = 2)
    private String nomArticle;

    @NotNull
    @JsonProperty(index = 3)
    private BigDecimal prixAchat;

    @JsonProperty(index = 4)
    private Integer volume;

    @JsonProperty(index = 5)
    private Double titrage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 6)
    private String nomMarque;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 7)
    private String nomCouleur;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 8)
    private String nomTypeBiere;

    @JsonProperty(index = 9)
    private Integer stock;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 10)
    private List<VendreDTO> ventes;

    public ArticleDTO(ArticleEntity articleEntity, boolean includeVentes) {
        this.id = articleEntity.getId();
        this.nomArticle = articleEntity.getNomArticle();
        this.prixAchat = articleEntity.getPrixAchat();
        this.volume = articleEntity.getVolume();
        this.titrage = articleEntity.getTitrage();
        this.stock = articleEntity.getStock();
        this.nomMarque = articleEntity.getMarque() != null ? articleEntity.getMarque().getNomMarque() : null;
        this.nomCouleur = articleEntity.getCouleur() != null ? articleEntity.getCouleur().getNomCouleur() : null;
        this.nomTypeBiere = articleEntity.getTypeBiere() != null ? articleEntity.getTypeBiere().getNomTypeBiere() : null;
        this.ventes = includeVentes ? VendreDTO.toDTOList(articleEntity.getVentes()) : null;
    }

    public static List<ArticleDTO> toDTOList(List<ArticleEntity> articleEntities, boolean includeVentes) {
        return articleEntities.stream()
                .sorted(Comparator.comparing(ArticleEntity::getId))
                .map(article -> new ArticleDTO(article, includeVentes))
                .collect(Collectors.toList());
    }
}