package fr.mb.repositories;

import fr.mb.entities.CouleurEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class CouleurRepository implements PanacheRepositoryBase<CouleurEntity, Integer> {
}