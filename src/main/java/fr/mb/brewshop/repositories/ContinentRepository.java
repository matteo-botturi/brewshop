package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.ContinentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ContinentRepository implements PanacheRepositoryBase<ContinentEntity, Integer> {
}