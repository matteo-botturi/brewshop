package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.CouleurEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
public class CouleurRepository implements PanacheRepositoryBase<CouleurEntity, Integer> {
    public Optional<CouleurEntity> findByNom(String nomCouleur) {
        return find("nomCouleur", nomCouleur).firstResultOptional();
    }
}