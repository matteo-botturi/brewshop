package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.MarqueDTO;
import fr.mb.brewshop.entities.MarqueEntity;
import fr.mb.brewshop.repositories.MarqueRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/marques")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarqueResources {

    @Inject
    MarqueRepository marqueRepository;

    @GET
    public Response getAllMarques() {
        List<MarqueEntity> marques = marqueRepository.listAll();
        return Response.ok(MarqueDTO.toDTOList(marques, true)).build();
    }
}
