package fr.mb.brewshop.resources;

import fr.mb.brewshop.DataInitializer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/test")
@ApplicationScoped
public class TestResources {

    @Inject
    DataInitializer dataInitializer;

    @GET
    @Path("/populate")
    @Transactional
    public Response testPopulate() {
        dataInitializer.populateDatabase();
        return Response.ok("Database popolato").build();
    }
}

