package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.ArticleDTO;
import fr.mb.brewshop.dto.VendreDTO;
import fr.mb.brewshop.entities.*;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.*;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.math.BigDecimal;
import java.util.List;

@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResources {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    VendreRepository vendreRepository;

    @Inject
    MarqueRepository marqueRepository;

    @Inject
    CouleurRepository couleurRepository;

    @Inject
    TypeBiereRepository typeBiereRepository;

    @GET
    @Operation(summary = "Obtenir tous les articles", description = "Récupérer la liste de tous les articles")
    @APIResponse(responseCode = "200", description = "Liste des articles récupérée avec succès")
    public Response getAll(@QueryParam("includeVentes") @DefaultValue("false") boolean includeVentes) {
        List<ArticleEntity> articleEntities = articleRepository.listAll();
        List<ArticleDTO> articleDTOs = ArticleDTO.toDTOList(articleEntities, includeVentes);  // Includi o escludi le vendite
        return Response.ok(articleDTOs).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtenir un article par ID", description = "Récupérer les détails d'un article spécifique")
    @APIResponse(responseCode = "200", description = "Détails de l'article récupérés avec succès")
    @APIResponse(responseCode = "404", description = "Article non trouvé")
    public Response getById(@PathParam("id") Integer id, @QueryParam("includeVentes") @DefaultValue("false") boolean includeVentes) {
        return articleRepository.findByIdOptional(id)
                .map(article -> Response.ok(new ArticleDTO(article, includeVentes)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/articles/{articleId}/ventes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir toutes les ventes d'un article", description = "Récupérer toutes les ventes associées à un article spécifique")
    @APIResponse(responseCode = "200", description = "Liste des ventes récupérée avec succès")
    @APIResponse(responseCode = "404", description = "Article non trouvé")
    public Response getAllVentesById(@PathParam("articleId") Integer articleId) {
        ArticleEntity article = articleRepository.findById(articleId);
        if (article == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Article non trouvé.\"}").build();

        List<VendreEntity> ventes = vendreRepository.find("article.id", articleId).list();
        if (ventes.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Aucune vente trouvée pour cet article.\"}").build();
        return Response.ok(VendreDTO.toDTOList(ventes)).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Recherche des articles", description = "Rechercher des articles par nom, prix, volume, etc.")
    @APIResponse(responseCode = "200", description = "Articles trouvés")
    public Response getArticlesByCriteria(
            @QueryParam("nom") String nom,
            @QueryParam("prixMin") BigDecimal prixMin,
            @QueryParam("prixMax") BigDecimal prixMax,
            @QueryParam("volume") Integer volume,
            @QueryParam("nomMarque") String nomMarque,
            @QueryParam("nomCouleur") String nomCouleur,
            @QueryParam("nomTypeBiere") String nomTypeBiere) {

        PanacheQuery<ArticleEntity> query = articleRepository.buildArticleQuery(nom, prixMin, prixMax, volume, nomMarque, nomCouleur, nomTypeBiere);
        List<ArticleEntity> articles = query.list();
        if (articles.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Aucun article trouvé.\"}").build();
        return Response.ok(ArticleDTO.toDTOList(articles, false)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Modifier un article", description = "Mettre à jour les détails d'un article existant")
    @APIResponse(responseCode = "200", description = "Article mis à jour avec succès")
    @APIResponse(responseCode = "404", description = "Article non trouvé")
    @Transactional
    public Response update(@PathParam("id") Integer id, ArticleDTO articleDTO) {
        ArticleEntity articleEntity = articleRepository.findById(id);
        if (articleEntity == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Article non trouvé.\"}").build();

        if (articleDTO.getNomArticle() != null)
            articleEntity.setNomArticle(StringFormatterService.formatOthersName(articleDTO.getNomArticle()));

        if (articleDTO.getPrixAchat() != null)
            articleEntity.setPrixAchat(articleDTO.getPrixAchat());

        if (articleDTO.getVolume() != null)
            articleEntity.setVolume(articleDTO.getVolume());

        if (articleDTO.getTitrage() != null)
            articleEntity.setTitrage(articleDTO.getTitrage());

        if (articleDTO.getNomMarque() != null) {
            MarqueEntity marque = marqueRepository.find("nomMarque", StringFormatterService.formatOthersName(articleDTO.getNomMarque())).firstResult();
            if (marque != null)
                articleEntity.setMarque(marque);
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Marque non trouvée.\"}").build();
        }

        if (articleDTO.getNomCouleur() != null) {
            CouleurEntity couleur = couleurRepository.find("nomCouleur", StringFormatterService.singleWord(articleDTO.getNomCouleur())).firstResult();
            if (couleur != null)
                articleEntity.setCouleur(couleur);
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Couleur non trouvée.\"}").build();
        }

        if (articleDTO.getNomTypeBiere() != null) {
            TypeBiereEntity typeBiere = typeBiereRepository.find("nomTypeBiere", StringFormatterService.formatOthersName(articleDTO.getNomTypeBiere())).firstResult();
            if (typeBiere != null)
                articleEntity.setTypeBiere(typeBiere);
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Type non trouvé.\"}").build();
        }
        articleRepository.persist(articleEntity);
        return Response.ok(new ArticleDTO(articleEntity, false)).build();
    }
}