package fr.mb.brewshop.entities;

import fr.mb.brewshop.outils.VendrePK;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "VENDRE", schema = "dbo")
public class VendreEntity {
    @EmbeddedId
    private VendrePK id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "annee", referencedColumnName = "ANNEE"),
            @JoinColumn(name = "numeroTicket", referencedColumnName = "NUMERO_TICKET")
    })
    private TicketEntity ticketEntity;

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

}