package fr.mb.brewshop;

import fr.mb.brewshop.entities.ContinentEntity;
import fr.mb.brewshop.entities.CouleurEntity;
import fr.mb.brewshop.entities.PaysEntity;
import fr.mb.brewshop.entities.TicketEntity;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.ContinentRepository;
import fr.mb.brewshop.repositories.CouleurRepository;
import fr.mb.brewshop.repositories.PaysRepository;
import fr.mb.brewshop.repositories.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@ApplicationScoped
@Transactional
public class DataInitializer {

    @Inject
    CouleurRepository couleurRepository;

    @Inject
    ContinentRepository continentRepository;

    @Inject
    PaysRepository paysRepository;

    @Inject
    TicketRepository ticketRepository;

    @PostConstruct
    public void populateDatabase() {

        // Tickets
        if (ticketRepository.count() == 0) {
            TicketEntity ticket1 = new TicketEntity(new TicketPK(2023, 1), LocalDate.of(2023, 9, 20), LocalTime.of(10, 0));
            TicketEntity ticket2 = new TicketEntity(new TicketPK(2023, 2), LocalDate.of(2023, 9, 21), LocalTime.of(11, 0));
            ticketRepository.persist(ticket1);
            ticketRepository.persist(ticket2);
        }

        // Continents et Pays
        if(continentRepository.count() == 0){
            ContinentEntity continent1 = new ContinentEntity("Europe");
            ContinentEntity continent2 = new ContinentEntity("Asia");
            continentRepository.persist(continent1);
            continentRepository.persist(continent2);

            if(paysRepository.count() == 0){
                PaysEntity pays1 = new PaysEntity("Italie", continent1);
                PaysEntity pays2 = new PaysEntity("France", continent1);
                PaysEntity pays3 = new PaysEntity("Chine", continent2);
                paysRepository.persist(pays1);
                paysRepository.persist(pays2);
                paysRepository.persist(pays3);
            }
        }

        // Couleurs
        if(couleurRepository.count() == 0){
            CouleurEntity couleur1 = new CouleurEntity("Blanche");
            CouleurEntity couleur2 = new CouleurEntity("Blonde");
            couleurRepository.persist(couleur1);
            couleurRepository.persist(couleur2);
        }
    }
}