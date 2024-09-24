package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.CountryDTO;
import fr.mb.brewshop.entities.CountryEntity;
import fr.mb.brewshop.repositories.CountryRepository;
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

@Path("/countries/")
@Tag(name = "Country")
@Produces(MediaType.APPLICATION_JSON)
public class CountryResources {
    @Inject
    private CountryRepository countryRepository;

    @GET
    @Operation(summary = "Get all countries", description = "Retrieve the list of all countries")
    @APIResponse(responseCode = "200", description = "List of countries retrieved successfully")
    public Response getAllCountries() {
        List<CountryEntity> countries = countryRepository.listAll();
        return Response.ok(CountryDTO.toDTOList(countries)).build();
    }

    @GET
    @Operation(summary = "Country by ID", description = "Select a country by its ID.")
    @APIResponse(responseCode = "200", description = "Ok, country found.")
    @APIResponse(responseCode = "404", description = "Country not found.")
    @Path("{id}")
    public Response getById(@PathParam("id") Integer id){
        return countryRepository.findByIdOptional(id)
                .map(country -> Response.ok(new CountryDTO(country)).build())
                .orElse(Response.status(404, "Ce pays n'existe pas.").build());
    }
}