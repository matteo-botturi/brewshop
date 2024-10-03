package fr.mb.brewshop.resources;

import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.dto.CouleurDTO;
import fr.mb.brewshop.entities.CouleurEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.CouleurRepository;
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

@Path("/couleurs/")
@Tag(name = "Couleur", description = "Opérations liées aux couleurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CouleurResources {

    @Inject
    CouleurRepository couleurRepository;

    @GET
    @Operation(summary = "Obtenir tous les couleurs", description = "Récupérer la liste de tous les couleurs")
    @APIResponse(responseCode = "200", description = "Liste des couleurs récupérée avec succès")
    @JsonView(Views.Internal.class)
    public Response getAll(){
        List<CouleurEntity> couleurEntities = couleurRepository.listAll();
        return Response.ok(CouleurDTO.toDTOList(couleurEntities)).build();
    }

    @GET
    @Operation(summary = "Couleur par ID", description = "Sélectionner une couleur par son ID.")
    @APIResponse(responseCode = "200", description = "Ok, couleur trouvée.")
    @APIResponse(responseCode = "404", description = "Couleur non trouvée.")
    @Path("{id}")
    @JsonView(Views.Internal.class)
    public Response getById(@PathParam("id") Integer id){
        return couleurRepository.findByIdOptional(id)
                .map(couleur -> Response.ok(new CouleurDTO(couleur)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @Transactional
    @POST
    @APIResponse(responseCode = "201", description = "La ressource a été créée.")
    @APIResponse(responseCode = "400", description = "Nom de couleur invalide.")
    @APIResponse(responseCode = "409", description = "La couleur existe déjà.")
    @APIResponse(responseCode = "500", description = "Le serveur a rencontré un problème.")
    @JsonView(Views.Public.class)
    public Response create(@Valid @JsonView(Views.Public.class) CouleurDTO couleurDTO, @Context UriInfo uriInfo) {
        String formattedNomCouleur = StringFormatterService.singleWord(couleurDTO.getNomCouleur());

        boolean exists = couleurRepository.find("nomCouleur", formattedNomCouleur).firstResultOptional().isPresent();
        if (exists) {
            String errorMessage = "{\"message\": \"La couleur existe déjà.\"}";
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        CouleurEntity couleurEntity = new CouleurEntity();
        couleurEntity.setNomCouleur(formattedNomCouleur);
        couleurRepository.persist(couleurEntity);

        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.path(couleurEntity.getId().toString());
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("{id}")
    @APIResponse(responseCode = "200", description = "La couleur a été mise à jour avec succès.")
    @APIResponse(responseCode = "400", description = "Nom de couleur invalide.")
    @APIResponse(responseCode = "404", description = "Couleur non trouvée.")
    @Transactional
    @JsonView(Views.Public.class)
    public Response update(@PathParam("id") Integer id, @Valid @JsonView(Views.Public.class) CouleurDTO couleurDTO) {
        return couleurRepository.findByIdOptional(id).map(couleur -> {
            String formattedNewColorName = StringFormatterService.singleWord(couleurDTO.getNomCouleur());
            couleur.setNomCouleur(formattedNewColorName);
            return Response.ok(new CouleurDTO(couleur)).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @APIResponse(responseCode = "204", description = "La couleur a été supprimée avec succès.")
    @APIResponse(responseCode = "404", description = "Couleur non trouvée.")
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        if (couleurRepository.deleteById(id))
            return Response.noContent().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}