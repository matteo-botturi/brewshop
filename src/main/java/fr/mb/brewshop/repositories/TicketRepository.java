package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.outils.TicketPK;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
public class TicketRepository implements PanacheRepositoryBase<TicketEntity, TicketPK> {
    public List<TicketEntity> findByAnnee(Integer annee) {
        return list("ticketPk.annee = ?1", annee);
    }
}