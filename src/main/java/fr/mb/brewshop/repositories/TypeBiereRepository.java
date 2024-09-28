package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.TypeBiereEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TypeBiereRepository implements PanacheRepositoryBase<TypeBiereEntity, Integer> {
}
