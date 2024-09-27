package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.PaysEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PaysRepository implements PanacheRepositoryBase<PaysEntity, Integer> {
}