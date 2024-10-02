package fr.mb.brewshop;

import fr.mb.brewshop.entities.*;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.*;
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

    @Inject
    FabricantRepository fabricantRepository;

    @Inject
    MarqueRepository marqueRepository;

    @PostConstruct
    public void populateDatabase() {
        TicketEntity ticket1 = new TicketEntity(new TicketPK(2023, 1), LocalDate.of(2023, 9, 20), LocalTime.of(10, 0));
        TicketEntity ticket2 = new TicketEntity(new TicketPK(2023, 2), LocalDate.of(2023, 9, 21), LocalTime.of(11, 0));

        ContinentEntity continent1 = new ContinentEntity("Europe");
        ContinentEntity continent2 = new ContinentEntity("Asia");

        PaysEntity pays1 = new PaysEntity("Italie", continent1);
        PaysEntity pays2 = new PaysEntity("France", continent1);
        PaysEntity pays3 = new PaysEntity("Chine", continent2);

        FabricantEntity fabricant1 = new FabricantEntity("Luppolajo");
        FabricantEntity fabricant2 = new FabricantEntity("Birrificio Inesistente");

        MarqueEntity marque1 = new MarqueEntity("Heineken", pays1, fabricant1);
        MarqueEntity marque2 = new MarqueEntity("Becks", pays2, fabricant2);

        CouleurEntity couleur1 = new CouleurEntity("Blanche");
        CouleurEntity couleur2 = new CouleurEntity("Blonde");

        // Tickets
        if (ticketRepository.count() == 0) {
            ticketRepository.persist(ticket1);
            ticketRepository.persist(ticket2);
        }

        // Continents
        if(continentRepository.count() == 0) {
            continentRepository.persist(continent1);
            continentRepository.persist(continent2);
        }

        //Pays
        if(paysRepository.count() == 0) {
            paysRepository.persist(pays1);
            paysRepository.persist(pays2);
            paysRepository.persist(pays3);
        }

        // Fabricant
        if(fabricantRepository.count() == 0) {

            fabricantRepository.persist(fabricant1);
            fabricantRepository.persist(fabricant2);
        }

        // Marque
        if(marqueRepository.count() == 0){
            marqueRepository.persist(marque1);
            marqueRepository.persist(marque2);
        }

        // Couleurs
        if(couleurRepository.count() == 0){
            couleurRepository.persist(couleur1);
            couleurRepository.persist(couleur2);
        }
    }
}