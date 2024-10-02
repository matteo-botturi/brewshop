package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.ContinentDTO;
import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.outils.StringFormatterService;
import fr.mb.brewshop.repositories.ContinentRepository;
import fr.mb.brewshop.repositories.PaysRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;

@Path("/continents/")
@Tag(name = "Continents", description = "Opérations liées aux continents")
@Produces(MediaType.APPLICATION_JSON)
public class ContinentResources {

    @Inject
    ContinentRepository continentRepository;

    @Inject
    PaysRepository paysRepository;

    @Context
    private UriInfo uriInfo;

    @GET
    @Operation(summary = "Obtenir tous les continents", description = "Récupérer la liste de tous les continents")
    @APIResponse(responseCode = "200", description = "Liste des continents récupérée avec succès")
    public Response getAll() {
        List<ContinentEntity> continentEntities = continentRepository.listAll();
        List<ContinentDTO> continentDTOs = ContinentDTO.toDtoList(continentEntities);
        return Response.ok(continentDTOs).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Continent par ID", description = "Rechercher un continent par son ID")
    @APIResponse(responseCode = "200", description = "Ok, continent trouvé")
    @APIResponse(responseCode = "404", description = "Continent non trouvé")
    public Response getById(@PathParam("id") Integer id, @QueryParam("includePays") @DefaultValue("false") boolean includePays) {
        ContinentEntity continent = continentRepository.findById(id);
        if (continent == null) return Response.status(Response.Status.NOT_FOUND).build();
        ContinentDTO continentDTO = new ContinentDTO(continent, includePays);
        return Response.ok(continentDTO).build();
    }

    @Transactional
    @POST
    @Path("{id}/{name}")
    @Operation(summary = "Créer un nouveau pays", description = "Ajouter un nouveau pays à un continent existant")
    @APIResponse(responseCode = "201", description = "Le pays a été créé avec succès")
    @APIResponse(responseCode = "400", description = "Nom de pays invalide")
    @APIResponse(responseCode = "404", description = "Continent non trouvé")
    @APIResponse(responseCode = "409", description = "Le pays existe déjà pour ce continent")
    public Response createPays(@PathParam("id") Integer id, @PathParam("name") String nomPays) {
        if (nomPays == null || nomPays.isBlank()){
            String errorMessage = "{\"message\": \"Le nom du pays ne peut pas être vide.\"}";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        ContinentEntity continentEntity = continentRepository.findById(id);
        if (continentEntity == null) return Response.status(Response.Status.NOT_FOUND).build();

        String formattedNomPays = StringFormatterService.formatCountryName(nomPays);

        boolean paysExists = continentEntity.getListPays().stream()
                .anyMatch(country ->
                        StringFormatterService.formatCountryName(country.getNomPays())
                                .equalsIgnoreCase(formattedNomPays));
        if (paysExists) {
            String errorMessage = "{\"message\": \"Le pays '" + formattedNomPays + "' existe déjà pour ce continent.\"}";
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        PaysEntity paysEntity = new PaysEntity();
        paysEntity.setNomPays(formattedNomPays);
        paysEntity.setContinent(continentEntity);
        paysRepository.persistAndFlush(paysEntity);
        continentEntity.getListPays().add(paysEntity);

        URI countryUri = uriInfo.getBaseUriBuilder().path("/pays/" + paysEntity.getId()).build();
        //URI baseUri = uriInfo.getBaseUri();
        ContinentDTO continentDTO = new ContinentDTO(continentEntity, true/*, baseUri*/);

        //return Response.ok(continentDTO).build();
        return Response.created(countryUri).entity(continentDTO).build();
    }
}