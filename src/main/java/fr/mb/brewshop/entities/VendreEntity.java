package fr.mb.brewshop.entities;

import fr.mb.brewshop.outils.VendrePK;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VENDRE", schema = "dbo")
public class VendreEntity {
    @EmbeddedId
    private VendrePK id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "ANNEE", referencedColumnName = "ANNEE"),
            @JoinColumn(name = "NUMERO_TICKET", referencedColumnName = "NUMERO_TICKET")
    })
    private TicketEntity ticket;

    @MapsId("idArticle")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ARTICLE", nullable = false)
    private ArticleEntity article;

    @NotNull
    @Column(name = "QUANTITE", nullable = false)
    private Integer quantite;

    @NotNull
    @Column(name = "PRIX_VENTE", nullable = false)
    private BigDecimal prixVente;

    //Only for DataInitializer
    public VendreEntity(TicketEntity ticket, ArticleEntity article, Integer quantite, BigDecimal prixVente) {
        this.ticket = ticket;
        this.article = article;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }
}