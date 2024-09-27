package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.PaysDTO;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.repositories.PaysRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    public Response getAllCountries() {
        List<PaysEntity> listPays = paysRepository.listAll();
        return Response.ok(PaysDTO.toDTOList(listPays)).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Pays par ID", description = "Sélectionner un pays par son ID.")
    @APIResponse(responseCode = "200", description = "Ok, pays trouvé")
    @APIResponse(responseCode = "404", description = "Pays non trouvé")
    public Response getById(@PathParam("id") Integer id){
        return paysRepository.findByIdOptional(id)
                .map(pays -> Response.ok(new PaysDTO(pays)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}