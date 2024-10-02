package fr.mb.brewshop.resources;

import com.fasterxml.jackson.annotation.JsonView;
import fr.mb.brewshop.dto.PaysDTO;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.PaysRepository;
import fr.mb.brewshop.views.Views;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/pays/")
@Tag(name = "Pays", description = "Operations liées aux Pays")
@Produces(MediaType.APPLICATION_JSON)
public class PaysResources {
    @Inject
    PaysRepository paysRepository;

    @GET
    @Operation(summary = "Obtenir tous les pays", description = "Récupérer la liste de tous les pays")
    @APIResponse(responseCode = "200", description = "Liste des pays récupérée avec succès")
    public Response getAll() {
        List<PaysEntity> listPays = paysRepository.listAll();
        return Response.ok(PaysDTO.toDTOList(listPays)).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Pays par ID", description = "Sélectionner un pays par son ID.")
    @APIResponse(responseCode = "200", description = "Ok, pays trouvé")
    @APIResponse(responseCode = "404", description = "Pays non trouvé")
    public Response getById(@PathParam("id") Integer id, @QueryParam("includeMarques") @DefaultValue("false") boolean includeMarques){
        return paysRepository.findByIdOptional(id)
                .map(pays -> Response.ok(new PaysDTO(pays, includeMarques)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Mettre à jour le nom d'un pays", description = "Modifier uniquement le nom d'un pays")
    @APIResponse(responseCode = "200", description = "Pays mis à jour avec succès")
    @APIResponse(responseCode = "404", description = "Pays non trouvé")
    @Transactional
    public Response updatePays(@PathParam("id") Integer id, String nomPays) {
        PaysEntity pays = paysRepository.findById(id);
        if (pays == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        pays.setNomPays(StringFormatterService.formatCountryName(nomPays));
        paysRepository.persist(pays);
        return Response.ok(new PaysDTO(pays)).build();
    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Supprimer un pays", description = "Supprimer un pays par son ID")
    @APIResponse(responseCode = "204", description = "Pays supprimé avec succès")
    @APIResponse(responseCode = "404", description = "Pays non trouvé")
    @Transactional
    public Response deletePays(@PathParam("id") Integer id) {
        boolean deleted = paysRepository.deleteById(id);
        if (deleted)
            return Response.noContent().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}