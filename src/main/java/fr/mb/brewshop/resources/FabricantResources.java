package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.FabricantDTO;
import fr.mb.brewshop.entities.FabricantEntity;
import fr.mb.brewshop.repositories.FabricantRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/fabricants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricantResources {

    @Inject
    FabricantRepository fabricantRepository;

    @GET
    public Response getAllFabricants() {
        List<FabricantEntity> fabricants = fabricantRepository.listAll();
        return Response.ok(FabricantDTO.toDtoList(fabricants)).build();
    }
}
