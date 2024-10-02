package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.FabricantEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
public class FabricantRepository implements PanacheRepositoryBase<FabricantEntity, Integer> {
    public Optional<FabricantEntity> findByNom(String nomFabricant) {
        return find("nomFabricant", nomFabricant).firstResultOptional();
    }
}
