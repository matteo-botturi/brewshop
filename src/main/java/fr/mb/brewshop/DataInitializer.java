package fr.mb.brewshop;

import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@ApplicationScoped
@Transactional
public class DataInitializer {

    @Inject
    TicketRepository ticketRepository;

    @PostConstruct
    public void populateDatabase() {
        System.out.println("Popolamento del database avviato...");  // Aggiungi questo per verificare
        if (ticketRepository.count() == 0) {  // Controlla se ci sono già ticket nel DB
            TicketEntity ticket1 = new TicketEntity(new TicketPK(2023, 1), LocalDate.of(2023, 9, 20), LocalTime.of(10, 0));
            TicketEntity ticket2 = new TicketEntity(new TicketPK(2023, 2), LocalDate.of(2023, 9, 21), LocalTime.of(11, 0));
            ticketRepository.persist(ticket1);
            ticketRepository.persist(ticket2);
            System.out.println("Popolamento del database completato.");
        }
    }
}


