package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.PaysEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
public class PaysRepository implements PanacheRepositoryBase<PaysEntity, Integer> {
    public Optional<PaysEntity> findByNom(String nomPays) {
        return find("nomPays", nomPays).firstResultOptional();
    }
}