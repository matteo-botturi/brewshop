package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.ContinentDTO;
import fr.mb.brewshop.dto.FabricantDTO;
import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.FabricantEntity;
import fr.mb.brewshop.entities.MarqueEntity;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.FabricantRepository;
import fr.mb.brewshop.repositories.MarqueRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;

@Path("/fabricants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricantResources {

    @Inject
    FabricantRepository fabricantRepository;

    @Inject
    MarqueRepository marqueRepository;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAll() {
        List<FabricantEntity> fabricants = fabricantRepository.listAll();
        return Response.ok(FabricantDTO.toDtoList(fabricants)).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Fabricant par ID", description = "Rechercher un fabricant par son ID")
    @APIResponse(responseCode = "200", description = "Ok, fabricant trouvé")
    @APIResponse(responseCode = "404", description = "Fabricant non trouvé")
    public Response getById(@PathParam("id") int id, @QueryParam("includeMarques") @DefaultValue("false") boolean includeMarques) {
        FabricantEntity fabricantEntity = fabricantRepository.findById(id);
        if (fabricantEntity == null) return Response.status(Response.Status.NOT_FOUND).build();
        FabricantDTO fabricantDTO = new FabricantDTO(fabricantEntity, includeMarques);
        return Response.ok(fabricantDTO).build();
    }

    @Transactional
    @POST
    @Path("{id}/{name}")
    @Operation(summary = "Créer une nouvelle marque", description = "Ajouter une nouvelle marque à un fabricant existant")
    @APIResponse(responseCode = "201", description = "La marque a été créée avec succès")
    @APIResponse(responseCode = "400", description = "Nom de la marque invalide")
    @APIResponse(responseCode = "404", description = "Fabricant non trouvé")
    @APIResponse(responseCode = "409", description = "La marque existe déjà pour ce fabricant")
    public Response createMarque(@PathParam("id") Integer id, @PathParam("name") String nomMarque) {
        if (nomMarque == null || nomMarque.isBlank()){
            String errorMessage = "{\"message\": \"Le nom de la marque ne peut pas être vide.\"}";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        FabricantEntity fabricantEntity = fabricantRepository.findById(id);
        if (fabricantEntity == null) return Response.status(Response.Status.NOT_FOUND).build();

        String formattedNomMarque = StringFormatterService.formatCountryName(nomMarque);

        boolean marqueExists = fabricantEntity.getMarques().stream()
                .anyMatch(fabricant ->
                        StringFormatterService.formatOthersName(fabricant.getNomMarque())
                                .equalsIgnoreCase(formattedNomMarque));
        if (marqueExists) {
            String errorMessage = "{\"message\": \"Le pays '" + formattedNomMarque + "' existe déjà pour ce fabricant.\"}";
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        MarqueEntity marqueEntity = new MarqueEntity();
        marqueEntity.setNomMarque(formattedNomMarque);
        marqueEntity.setFabricant(fabricantEntity);
        marqueRepository.persistAndFlush(marqueEntity);
        fabricantEntity.getMarques().add(marqueEntity);

        URI countryUri = uriInfo.getBaseUriBuilder().path("/fabricants/" + marqueEntity.getId()).build();
        //URI baseUri = uriInfo.getBaseUri();
        FabricantDTO fabricantDTO = new FabricantDTO(fabricantEntity, true/*, baseUri*/);

        //return Response.ok(fabricantDTO).build();
        return Response.created(countryUri).entity(fabricantDTO).build();
    }
}