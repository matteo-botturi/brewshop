package fr.mb.brewshop.resources;

import fr.mb.brewshop.dto.TicketDTO;
import fr.mb.brewshop.dto.VendreDTO;
import fr.mb.brewshop.entities.ArticleEntity;
import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.entities.VendreEntity;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.ArticleRepository;
import fr.mb.brewshop.repositories.TicketRepository;
import fr.mb.brewshop.repositories.VendreRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    @Inject
    VendreRepository vendreRepository;

    @Inject
    ArticleRepository articleRepository;

    @GET
    @Operation(summary = "Obtenir tous les tickets", description = "Récupérer la liste de tous les tickets")
    @APIResponse(responseCode = "200", description = "Liste des tickets récupérée avec succès")
    public Response getAll(){
        List<TicketEntity> ticketEntities = ticketRepository.listAll();
        List<TicketDTO> ticketDTOs = TicketDTO.toDTOList(ticketEntities);
        return Response.ok(ticketDTOs).build();
    }

    @GET
    @Path("{annee}")
    @Operation(summary = "Tickets par année", description = "Récupérer les tickets d'une année spécifique")
    @APIResponse(responseCode = "200", description = "Liste des tickets de l'année récupérée avec succès")
    @APIResponse(responseCode = "404", description = "Aucun ticket trouvé pour cette année")
    public Response getByAnnee(@PathParam("annee") Integer annee) {
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
    public Response getById(@PathParam("annee") Integer annee, @PathParam("numero") Integer numeroTicket) {
        TicketPK ticketPK = new TicketPK(annee, numeroTicket);
        TicketEntity ticket = ticketRepository.findById(ticketPK);
        if (ticket == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        TicketDTO ticketDTO = new TicketDTO(ticket);
        return Response.ok(ticketDTO).build();
    }

    @GET
    @Path("/tickets/{annee}/{numeroTicket}/ventes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtenir toutes les ventes d'un ticket", description = "Récupérer toutes les ventes associées à un ticket spécifique")
    @APIResponse(responseCode = "200", description = "Liste des ventes récupérée avec succès")
    public Response getAllVentesByIdTicket(@PathParam("annee") Integer annee, @PathParam("numeroTicket") Integer numeroTicket) {
        List<VendreEntity> ventes = vendreRepository.find("ticketEntity.id.annee = ?1 and ticketEntity.id.numeroTicket = ?2", annee, numeroTicket).list();
        if (ventes.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Aucune vente trouvée pour ce ticket.\"}").build();
        return Response.ok(VendreDTO.toDTOList(ventes)).build();
    }

    @POST
    @Path("/tickets/{annee}/{numeroTicket}/ventes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Ajouter une nouvelle vente", description = "Ajouter un nouvel article vendu à un ticket existant")
    @APIResponse(responseCode = "201", description = "Vente créée avec succès")
    @APIResponse(responseCode = "400", description = "Stock insuffisant ou validation des données échouée")
    @APIResponse(responseCode = "404", description = "Ticket ou Article non trouvé")
    @Transactional
    public Response createVente(@PathParam("annee") Integer annee, @PathParam("numeroTicket") Integer numeroTicket, VendreDTO vendreDTO) {
        TicketEntity ticket = ticketRepository.findById(new TicketPK(annee, numeroTicket));
        if (ticket == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Ticket non trouvé.\"}").build();

        ArticleEntity article = articleRepository.findById(vendreDTO.getIdArticle());
        if (article == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Article non trouvé.\"}").build();

        if (article.getStock() < vendreDTO.getQuantite())
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"Stock insuffisant.\"}").build();
        article.setStock(article.getStock() - vendreDTO.getQuantite());

        VendreEntity vente = new VendreEntity();
        vente.setTicket(ticket);
        vente.setArticle(article);
        vente.setQuantite(vendreDTO.getQuantite());
        vente.setPrixVente(vendreDTO.getPrixVente());

        vendreRepository.persist(vente);
        articleRepository.persist(article);
        return Response.status(Response.Status.CREATED).entity(new VendreDTO(vente)).build();
    }

    @PUT
    @Path("/tickets/{annee}/{numeroTicket}/ventes/{articleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Mettre à jour une vente", description = "Modifier la quantité ou le prix d'une vente")
    @APIResponse(responseCode = "200", description = "Vente mise à jour avec succès")
    @Transactional
    public Response updateVente(@PathParam("annee") Integer annee, @PathParam("numeroTicket") Integer numeroTicket, @PathParam("articleId") Integer articleId, VendreDTO vendreDTO) {
        VendreEntity vente = vendreRepository.find("ticketEntity.id.annee = ?1 and ticketEntity.id.numeroTicket = ?2 and article.id = ?3", annee, numeroTicket, articleId).firstResult();
        if (vente == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Vente non trouvée.\"}").build();

        vente.setQuantite(vendreDTO.getQuantite());
        vente.setPrixVente(vendreDTO.getPrixVente());
        vendreRepository.persist(vente);

        return Response.ok(new VendreDTO(vente)).build();
    }

    @DELETE
    @Path("/tickets/{annee}/{numeroTicket}/ventes/{articleId}")
    @Operation(summary = "Supprimer une vente", description = "Supprimer une vente par son ID")
    @APIResponse(responseCode = "204", description = "Vente supprimée avec succès")
    @Transactional
    public Response deleteVente(@PathParam("annee") Integer annee, @PathParam("numeroTicket") Integer numeroTicket, @PathParam("articleId") Integer articleId) {
        long deletedCount = vendreRepository.delete("ticketEntity.id.annee = ?1 and ticketEntity.id.numeroTicket = ?2 and article.id = ?3", annee, numeroTicket, articleId);
        if (deletedCount > 0)
            return Response.noContent().build();
        return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Vente non trouvée.\"}").build();
    }
}