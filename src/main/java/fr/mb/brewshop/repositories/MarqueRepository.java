package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.MarqueEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
public class MarqueRepository implements PanacheRepositoryBase<MarqueEntity, Integer> {
    public Optional<MarqueEntity> findByNom(String nomMarque) {
        return find("nomMarque", nomMarque).firstResultOptional();
    }
}