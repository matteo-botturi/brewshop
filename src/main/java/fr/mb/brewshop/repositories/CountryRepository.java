package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.CountryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class CountryRepository implements PanacheRepositoryBase<CountryEntity, Integer> {
}
