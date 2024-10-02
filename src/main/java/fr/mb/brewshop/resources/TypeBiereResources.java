package fr.mb.brewshop.resources;

import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.dto.ArticleDTO;
import fr.mb.brewshop.dto.CouleurDTO;
import fr.mb.brewshop.dto.TypeBiereDTO;
import fr.mb.brewshop.entities.ArticleEntity;
import fr.mb.brewshop.entities.CouleurEntity;
import fr.mb.brewshop.entities.TypeBiereEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.ArticleRepository;
import fr.mb.brewshop.repositories.TypeBiereRepository;
import fr.mb.brewshop.views.Views;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/types/")
@Tag(name = "Type", description = "Opérations liées aux types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TypeBiereResources {

    @Inject
    TypeBiereRepository typeBiereRepository;

    @Inject
    ArticleRepository articleRepository;

    @GET
    @Operation(summary = "Obtenir tous les types", description = "Récupérer la liste de tous les types")
    @APIResponse(responseCode = "200", description = "Liste des types récupérée avec succès")
    @JsonView(Views.Internal.class)
    public Response getAll(){
        List<TypeBiereEntity> typeBiereEntities = typeBiereRepository.listAll();
        return Response.ok(TypeBiereDTO.toDTOList(typeBiereEntities)).build();
    }

    @GET
    @Operation(summary = "Couleur par ID", description = "Sélectionner un type par son ID.")
    @APIResponse(responseCode = "200", description = "Ok, type trouvé.")
    @APIResponse(responseCode = "404", description = "Type non trouvé.")
    @Path("{id}")
    @JsonView(Views.Internal.class)
    public Response getById(@PathParam("id") Integer id){
        return typeBiereRepository.findByIdOptional(id)
                .map(typeBiere -> Response.ok(new TypeBiereDTO(typeBiere)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/{id}/articles")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir tous les articles d'un type de bière", description = "Récupérer la liste de tous les articles d'un type de bière spécifique")
    @APIResponse(responseCode = "200", description = "Liste des articles récupérée avec succès")
    public Response getArticlesByTypeBiere(@PathParam("id") Integer typeBiereId) {
        List<ArticleEntity> articles = articleRepository.find("typeBiere.id", typeBiereId).list();
        if (articles.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Aucun article trouvé pour ce type de bière.\"}").build();
        }
        return Response.ok(ArticleDTO.toDTOList(articles, false)).build();
    }


    @Transactional
    @POST
    @APIResponse(responseCode = "201", description = "La ressource a été créée.")
    @APIResponse(responseCode = "400", description = "Nom du type invalide.")
    @APIResponse(responseCode = "409", description = "Le type existe déjà.")
    @APIResponse(responseCode = "500", description = "Le serveur a rencontré un problème.")
    @JsonView(Views.Public.class)
    public Response create(@Valid @JsonView(Views.Public.class) TypeBiereDTO typeBiere, @Context UriInfo uriInfo) {
        String formattedNomTypeBiere = StringFormatterService.singleWord(typeBiere.getNom());

        boolean exists = typeBiereRepository.find("nomType", formattedNomTypeBiere).firstResultOptional().isPresent();
        if (exists) {
            String errorMessage = "{\"message\": \"Le type existe déjà.\"}";
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        TypeBiereEntity typeBiereEntity = new TypeBiereEntity();
        typeBiereEntity.setNomType(formattedNomTypeBiere);
        typeBiereRepository.persist(typeBiereEntity);

        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.path(typeBiereEntity.getId().toString());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("{id}")
    @APIResponse(responseCode = "200", description = "Le type a été mise à jour avec succès.")
    @APIResponse(responseCode = "400", description = "Nom du type invalide.")
    @APIResponse(responseCode = "404", description = "Type non trouvé.")
    @Transactional
    @JsonView(Views.Public.class)
    public Response updateById(@PathParam("id") Integer id, @Valid @JsonView(Views.Public.class) CouleurDTO couleurDTO) {
        return typeBiereRepository.findByIdOptional(id).map(typeBiere -> {
            String formattedNewTypeName = StringFormatterService.singleWord(couleurDTO.getNom());
            typeBiere.setNomType(formattedNewTypeName);
            return Response.ok(new TypeBiereDTO(typeBiere)).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @APIResponse(responseCode = "204", description = "Le type a été supprimé avec succès.")
    @APIResponse(responseCode = "404", description = "Type non trouvé.")
    @Path("{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Integer id){
        if (typeBiereRepository.deleteById(id))
            return Response.noContent().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}