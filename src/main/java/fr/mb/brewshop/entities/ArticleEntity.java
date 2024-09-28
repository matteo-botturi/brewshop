package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
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
    private Set<VendreEntity> ventes = new LinkedHashSet<>();

}