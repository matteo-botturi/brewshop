package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.MarqueEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MarqueRepository implements PanacheRepositoryBase<MarqueEntity, Integer> {

}
