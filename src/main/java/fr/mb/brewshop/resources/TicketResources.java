package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.TicketDTO;
import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.TicketRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/tickets/")
@Tag(name = "Ticket")
@Produces(MediaType.APPLICATION_JSON)
public class TicketResources {
    @Inject
    TicketRepository ticketRepository;

    @GET
    public Response getAllTickets(){
        List<TicketEntity> ticketEntities = ticketRepository.listAll();
        List<TicketDTO> ticketDTOs = TicketDTO.toDTOList(ticketEntities);
        return Response.ok(ticketDTOs).build();
    }

    @GET
    @Path("{annee}")
    public Response getTicketsByAnnee(@PathParam("annee") Integer annee) {
        List<TicketEntity> tickets = ticketRepository.findByAnnee(annee);
        if (tickets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Aucun ticket trouvé pour l'année " + annee)
                    .build();
        }
        List<TicketDTO> ticketDTOs = TicketDTO.toDTOList(tickets);
        return Response.ok(ticketDTOs).build();
    }


    @GET
    @Path("{annee}/{numero}")
    public Response getTicketById(@PathParam("annee") Integer annee, @PathParam("numero") Integer numeroTicket) {
        TicketPK ticketPK = new TicketPK(annee, numeroTicket);
        TicketEntity ticket = ticketRepository.findById(ticketPK);
        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .header("Error-Message","Le ticket n'existe pas.")
                    .build();
        }
        TicketDTO ticketDTO = new TicketDTO(ticket);
        return Response.ok(ticketDTO).build();
    }

}