package fr.mb.brewshop;

import fr.mb.brewshop.entities.*;
import fr.mb.brewshop.outils.TicketPK;
import fr.mb.brewshop.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
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

    @Inject
    TypeBiereRepository typeBiereRepository;

    @Inject
    ArticleRepository articleRepository;

    @Inject
    VendreRepository vendreRepository;

    @PostConstruct
    public void populateDatabase() {

        //Ticket
        TicketEntity ticket1 = new TicketEntity(new TicketPK(2023, 1), LocalDate.of(2023, 9, 20), LocalTime.of(10, 0));
        TicketEntity ticket2 = new TicketEntity(new TicketPK(2023, 2), LocalDate.of(2023, 9, 21), LocalTime.of(11, 0));
        if (ticketRepository.count() == 0) {
            ticketRepository.persist(ticket1);
            ticketRepository.persist(ticket2);
        }

        //Continent
        ContinentEntity continent1 = new ContinentEntity("Europe");
        ContinentEntity continent2 = new ContinentEntity("Asie");
        ContinentEntity continent3 = new ContinentEntity("Amérique du Nord");
        if(continentRepository.count() == 0) {
            continentRepository.persist(continent1);
            continentRepository.persist(continent2);
            continentRepository.persist(continent3);
        }

        //Pays
        PaysEntity pays1 = new PaysEntity("Italie", continent1);
        PaysEntity pays2 = new PaysEntity("France", continent1);
        PaysEntity pays3 = new PaysEntity("Chine", continent2);
        PaysEntity pays4 = new PaysEntity("Canada", continent3);
        PaysEntity pays5 = new PaysEntity("Belgique", continent1);
        if(paysRepository.count() == 0) {
            paysRepository.persist(pays1);
            paysRepository.persist(pays2);
            paysRepository.persist(pays3);
            paysRepository.persist(pays4);
            paysRepository.persist(pays5);
        }

        //Fabricant
        FabricantEntity fabricant1 = new FabricantEntity("Heineken");
        FabricantEntity fabricant2 = new FabricantEntity("AB InBev");
        FabricantEntity fabricant3 = new FabricantEntity("Carlsberg Group");
        if(fabricantRepository.count() == 0) {
            fabricantRepository.persist(fabricant1);
            fabricantRepository.persist(fabricant2);
            fabricantRepository.persist(fabricant3);
        }

        //Marque
        MarqueEntity marque1 = new MarqueEntity("Heineken", pays2, fabricant1);
        MarqueEntity marque2 = new MarqueEntity("Leffe", pays5, fabricant2);
        MarqueEntity marque3 = new MarqueEntity("Carlsberg", pays4, fabricant3);
        if(marqueRepository.count() == 0){
            marqueRepository.persist(marque1);
            marqueRepository.persist(marque2);
            marqueRepository.persist(marque3);
        }

        //Couleur
        CouleurEntity couleur1 = new CouleurEntity("Blanche");
        CouleurEntity couleur2 = new CouleurEntity("Blonde");
        CouleurEntity couleur3 = new CouleurEntity("Brune");
        CouleurEntity couleur4 = new CouleurEntity("Ambrée");
        if(couleurRepository.count() == 0){
            couleurRepository.persist(couleur1);
            couleurRepository.persist(couleur2);
            couleurRepository.persist(couleur3);
            couleurRepository.persist(couleur4);
        }

        //TypeBiere
        TypeBiereEntity type1 = new TypeBiereEntity("Lager");
        TypeBiereEntity type2 = new TypeBiereEntity("Pilsner");
        TypeBiereEntity type3 = new TypeBiereEntity("Ale");
        if(typeBiereRepository.count() == 0){
            typeBiereRepository.persist(type1);
            typeBiereRepository.persist(type2);
            typeBiereRepository.persist(type3);
        }

        //Article
        ArticleEntity article1 = new ArticleEntity("Leffe Blonde", BigDecimal.valueOf(2.50), 33, 6.6, marque2, couleur2, type3, 100);
        ArticleEntity article2 = new ArticleEntity("Heineken", BigDecimal.valueOf(1.80), 33, 5.0, marque1, couleur2, type1, 200);
        ArticleEntity article3 = new ArticleEntity("Carlsberg", BigDecimal.valueOf(2.00), 75, 5.0, marque3, couleur2, type1, 150);
        if(articleRepository.count() == 0){
            articleRepository.persist(article1);
            articleRepository.persist(article2);
            articleRepository.persist(article3);
        }

        //Vendre
        VendreEntity vendre1 = new VendreEntity(ticket1, article1, 2, BigDecimal.valueOf(5.00));
        VendreEntity vendre2 = new VendreEntity(ticket2, article2, 1, BigDecimal.valueOf(1.80));
        VendreEntity vendre3 = new VendreEntity(ticket2, article3, 3, BigDecimal.valueOf(6.00));
        if(vendreRepository.count() == 0){
            vendreRepository.persist(vendre1);
            vendreRepository.persist(vendre2);
            vendreRepository.persist(vendre3);
        }
    }
}