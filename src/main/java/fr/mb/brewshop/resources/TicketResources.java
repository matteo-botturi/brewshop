package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.TicketDTO;
import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.TicketRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/tickets/")
@Tag(name = "Ticket", description = "Opérations liées aux tickets")
@Produces(MediaType.APPLICATION_JSON)
public class TicketResources {
    @Inject
    TicketRepository ticketRepository;

    @GET
    @Operation(summary = "Obtenir tous les tickets", description = "Récupérer la liste de tous les tickets")
    @APIResponse(responseCode = "200", description = "Liste des tickets récupérée avec succès")
    public Response getAllTickets(){
        List<TicketEntity> ticketEntities = ticketRepository.listAll();
        List<TicketDTO> ticketDTOs = TicketDTO.toDTOList(ticketEntities);
        return Response.ok(ticketDTOs).build();
    }

    @GET
    @Path("{annee}")
    @Operation(summary = "Tickets par année", description = "Récupérer les tickets d'une année spécifique")
    @APIResponse(responseCode = "200", description = "Liste des tickets de l'année récupérée avec succès")
    @APIResponse(responseCode = "404", description = "Aucun ticket trouvé pour cette année")
    public Response getTicketsByAnnee(@PathParam("annee") Integer annee) {
        List<TicketEntity> tickets = ticketRepository.findByAnnee(annee);
        if (tickets.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        List<TicketDTO> ticketDTOs = TicketDTO.toDTOList(tickets);
        return Response.ok(ticketDTOs).build();
    }


    @GET
    @Path("{annee}/{numero}")
    @Operation(summary = "Ticket par ID", description = "Récupérer un ticket spécifique par son année et numéro")
    @APIResponse(responseCode = "200", description = "Ticket trouvé avec succès")
    @APIResponse(responseCode = "404", description = "Ticket non trouvé")
    public Response getTicketById(@PathParam("annee") Integer annee, @PathParam("numero") Integer numeroTicket) {
        TicketPK ticketPK = new TicketPK(annee, numeroTicket);
        TicketEntity ticket = ticketRepository.findById(ticketPK);
        if (ticket == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        TicketDTO ticketDTO = new TicketDTO(ticket);
        return Response.ok(ticketDTO).build();
    }
}