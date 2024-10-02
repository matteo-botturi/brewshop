package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.ArticleDTO;
import fr.mb.brewshop.dto.MarqueDTO;
import fr.mb.brewshop.entities.ArticleEntity;
import fr.mb.brewshop.entities.FabricantEntity;
import fr.mb.brewshop.entities.MarqueEntity;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.ArticleRepository;
import fr.mb.brewshop.repositories.FabricantRepository;
import fr.mb.brewshop.repositories.MarqueRepository;
import fr.mb.brewshop.repositories.PaysRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.util.List;
import java.util.Optional;

@Path("/marques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarqueResources {

    @Inject
    MarqueRepository marqueRepository;

    @Inject
    PaysRepository paysRepository;

    @Inject
    FabricantRepository fabricantRepository;

    @Inject
    ArticleRepository articleRepository;

    @GET
    @Operation(summary = "Obtenir toutes les marques", description = "Récupérer la liste de toutes les marques")
    @APIResponse(responseCode = "200", description = "Liste des marques récupérée avec succès")
    public Response getAll() {
        List<MarqueEntity> marques = marqueRepository.listAll();
        return Response.ok(MarqueDTO.toDTOList(marques)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtenir une marque par ID", description = "Récupérer les détails d'une marque spécifique")
    @APIResponse(responseCode = "200", description = "Détails de la marque récupérés avec succès")
    @APIResponse(responseCode = "404", description = "Marque non trouvée")
    public Response getById(@PathParam("id") Integer id, @QueryParam("includeArticles") @DefaultValue("false") boolean includeArticles) {
        return marqueRepository.findByIdOptional(id)
                .map(marque -> Response.ok(new MarqueDTO(marque, includeArticles)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/{id}/articles")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir tous les articles d'une marque", description = "Récupérer la liste de tous les articles d'une marque spécifique")
    @APIResponse(responseCode = "200", description = "Liste des articles récupérée avec succès")
    public Response getArticlesByMarque(@PathParam("id") Integer marqueId) {
        List<ArticleEntity> articles = articleRepository.find("marque.id", marqueId).list();
        if (articles.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Aucun article trouvé pour cette marque.\"}").build();
        }
        return Response.ok(ArticleDTO.toDTOList(articles, false)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Mettre à jour une marque", description = "Modifier le nom, le pays ou le fabricant d'une marque")
    @APIResponse(responseCode = "200", description = "Marque mise à jour avec succès")
    @APIResponse(responseCode = "404", description = "Marque, pays ou fabricant non trouvé")
    @Transactional
    public Response updateMarque(@PathParam("id") Integer id, MarqueDTO updatedMarqueDTO) {
        MarqueEntity marque = marqueRepository.findById(id);
        if (marque == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        if (updatedMarqueDTO.getNom() != null)
            marque.setNomMarque(StringFormatterService.singleWord(updatedMarqueDTO.getNom()));

        if (updatedMarqueDTO.getPays() != null) {
            Optional<PaysEntity> paysOpt = paysRepository.findByNom(updatedMarqueDTO.getPays());
            if (paysOpt.isEmpty())
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Pays avec le nom '" + updatedMarqueDTO.getPays() + "' non trouvé.\"}")
                        .build();
            marque.setPays(paysOpt.get());
        }

        if (updatedMarqueDTO.getFabricant() != null) {
            Optional<FabricantEntity> fabricantOpt = fabricantRepository.findByNom(updatedMarqueDTO.getFabricant());
            if (fabricantOpt.isEmpty())
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Fabricant avec le nom '" + updatedMarqueDTO.getFabricant() + "' non trouvé.\"}")
                        .build();
            marque.setFabricant(fabricantOpt.get());
        }
        marqueRepository.persist(marque);
        return Response.ok(new MarqueDTO(marque, true)).build();
    }


    @DELETE
    @Path("{id}")
    @Operation(summary = "Supprimer une marque", description = "Supprimer une marque par son ID")
    @APIResponse(responseCode = "204", description = "Marque supprimée avec succès")
    @APIResponse(responseCode = "404", description = "Marque non trouvée")
    @Transactional
    public Response deleteMarque(@PathParam("id") Integer id) {
        boolean deleted = marqueRepository.deleteById(id);
        if (deleted)
            return Response.noContent().build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Marque non trouvée.\"}")
                    .build();
    }
}
