package fr.mb.resources;

import fr.mb.dto.CouleurDTO;
import fr.mb.entities.CouleurEntity;
import fr.mb.repositories.CouleurRepository;
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
    private CouleurRepository couleurRepository;

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
        CouleurEntity couleurEntity = couleurRepository.findById(id);
        if(couleurEntity == null)
            return Response.status(404, "Cet identifiant n'existe pas.").build();
        return Response.ok(couleurEntity).build();
    }

    @Transactional
    @POST
    @APIResponse(responseCode = "201", description = "La resource a été crée.")
    @APIResponse(responseCode = "500", description = "Le serveur a rencontré un problème.")
    public Response create(String couleurName, @Context UriInfo uriInfo){
        CouleurEntity couleurEntity = new CouleurEntity();
        couleurEntity.setNom(couleurName);
        couleurRepository.persist(couleurEntity);
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.path(couleurEntity.getId().toString());
        return Response.created(uriBuilder.build()).build();
    }
}