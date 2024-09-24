package fr.mb.brewshop.resources;

import fr.mb.brewshop.repositories.TicketRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/tickets/")
@Tag(name = "Ticket")
@Produces(MediaType.APPLICATION_JSON)
public class TicketResources {
    @Inject
    TicketRepository ticketRepository;

    @GET
    public Response getAll(){
        return Response.ok(ticketRepository.listAll()).build();
    }
}
