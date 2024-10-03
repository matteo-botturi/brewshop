package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ARTICLE", schema = "dbo")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARTICLE", nullable = false)
    private Integer id;

    @Size(max = 60)
    @NotNull
    @Column(name = "NOM_ARTICLE", nullable = false, length = 60)
    private String nomArticle;

    @NotNull
    @Column(name = "PRIX_ACHAT", nullable = false)
    private BigDecimal prixAchat;

    @Column(name = "VOLUME")
    private Integer volume;

    @Column(name = "TITRAGE")
    private Double titrage;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_MARQUE", nullable = false)
    private MarqueEntity marque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COULEUR")
    private CouleurEntity couleur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TYPE")
    private TypeBiereEntity typeBiere;

    @Column(name = "STOCK")
    private Integer stock;

    @OneToMany(mappedBy = "article")
    private List<VendreEntity> ventes;

    //Only for DataInitializer
    public ArticleEntity(String nomArticle, BigDecimal prixAchat, Integer volume, Double titrage, MarqueEntity marque, CouleurEntity couleur, TypeBiereEntity typeBiere, Integer stock) {
        this.nomArticle = nomArticle;
        this.prixAchat = prixAchat;
        this.volume = volume;
        this.titrage = titrage;
        this.marque = marque;
        this.couleur = couleur;
        this.typeBiere = typeBiere;
        this.stock = stock;
    }
}