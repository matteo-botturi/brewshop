package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.ContinentDTO;
import fr.mb.brewshop.dto.CountryDTO;
import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.CountryEntity;
import fr.mb.brewshop.repositories.ContinentRepository;
import fr.mb.brewshop.repositories.CountryRepository;
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
@Tag(name = "Continents", description = "Operations related to continents")
@Produces(MediaType.APPLICATION_JSON)
public class ContinentResources {

    @Inject
    private ContinentRepository continentRepository;

    @Inject
    CountryRepository countryRepository;

    @Context
    private UriInfo uriInfo;

    @GET
    @Operation(summary = "Get all continents", description = "Retrieve the list of all continents")
    @APIResponse(responseCode = "200", description = "List of continents retrieved successfully")
    public Response getAll() {
        List<ContinentEntity> continentEntities = continentRepository.listAll();
        List<ContinentDTO> continentDTOs = ContinentDTO.toDtoList(continentEntities);
        return Response.ok(continentDTOs).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Continent by Id", description = "Search a continent by its ID")
    @APIResponse(responseCode = "200", description = "Ok, continent found")
    @APIResponse(responseCode = "404", description = "Continent not found")
    public Response getById(@PathParam("id") Integer id) {
        ContinentEntity continent = continentRepository.findById(id);
        if (continent == null) return Response.status(404,"Cet identifiant n'existe pas").build();
        ContinentDTO continentDTO = new ContinentDTO(continent);
        return Response.ok(continentDTO).build();
    }

    @Transactional
    @POST
    @Path("{id}/{name}")
    public Response createCountry(@PathParam("id") Integer id, @PathParam("name") String countryName) {
        ContinentEntity continentEntity = continentRepository.findById(id);
        if (continentEntity == null) return Response.status(404, "Not Found").build();

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setNomPays(countryName);
        countryEntity.setContinent(continentEntity);
        countryRepository.persistAndFlush(countryEntity);
        continentEntity.getCountries().add(countryEntity);

        URI baseUri = uriInfo.getBaseUri();
        ContinentDTO continentDTO = new ContinentDTO(continentEntity, true, baseUri);
        return Response.ok(continentDTO).build();
    }
}