package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.CouleurDTO;
import fr.mb.brewshop.entities.CouleurEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.CouleurRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/couleurs/")
@Tag(name = "Couleur")
@Produces(MediaType.APPLICATION_JSON)
public class CouleurResources {
    @Inject
    CouleurRepository couleurRepository;

    @GET
    public Response getAll(){
        List<CouleurEntity> couleurEntities = couleurRepository.listAll();
        return Response.ok(CouleurDTO.toDTOList(couleurEntities)).build();
    }

    @GET
    @Operation(summary = "Color by ID", description = "Select a color by its ID.")
    @APIResponse(responseCode = "200", description = "Ok, color found.")
    @APIResponse(responseCode = "404", description = "Color not found.")
    @Path("{id}")
    public Response getById(@PathParam("id") Integer id){
        return couleurRepository.findByIdOptional(id)
                .map(couleur -> Response.ok(new CouleurDTO(couleur)).build())
                .orElse(Response.status(404, "Ce couleur n'existe pas.").build());
    }

    @Transactional
    @POST
    @APIResponse(responseCode = "201", description = "La ressource a été créée.")
    @APIResponse(responseCode = "400", description = "Nom de couleur invalide.")
    @APIResponse(responseCode = "409", description = "Le couleur existe déjà.")
    @APIResponse(responseCode = "500", description = "Le serveur a rencontré un problème.")
    public Response create(String nomCouleur, @Context UriInfo uriInfo) {
        if (nomCouleur == null || nomCouleur.isBlank())
            return Response.status(Response.Status.BAD_REQUEST).entity("Le nom de la couleur ne peut pas être vide.").build();

        String formattedNomCouleur = StringFormatterService.singleWord(nomCouleur);
        boolean exists = couleurRepository.find("nomCouleur", formattedNomCouleur).firstResultOptional().isPresent();
        if (exists)
            return Response.status(Response.Status.CONFLICT).entity("Le couleur existe déjà.").build();

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
    public Response update(@PathParam("id") Integer id, String newColorName) {
        if (newColorName == null || newColorName.isBlank())
            return Response.status(Response.Status.BAD_REQUEST).entity("Le nom de la couleur ne peut pas être vide.").build();

        return couleurRepository.findByIdOptional(id)
                .map(couleur -> {
                    String formattedNewColorName = StringFormatterService.singleWord(newColorName);
                    couleur.setNomCouleur(formattedNewColorName);
                    return Response.ok(new CouleurDTO(couleur)).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).entity("Couleur non trouvée.").build());
    }

    @Transactional
    @DELETE
    @APIResponse(responseCode = "204", description = "Le couleur a été supprimée avec succès.")
    @APIResponse(responseCode = "404", description = "Couleur non trouvée.")
    @Path("{id}")
    public Response deleteById(@PathParam("id") Integer id){
        if (couleurRepository.deleteById(id))
            return Response.noContent().build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}