package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.FabricantEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class FabricantRepository implements PanacheRepositoryBase<FabricantEntity, Integer> {
}
