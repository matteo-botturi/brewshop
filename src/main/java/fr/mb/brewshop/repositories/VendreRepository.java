package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.VendreEntity;
import fr.mb.brewshop.outils.VendrePK;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class VendreRepository implements PanacheRepositoryBase<VendreEntity, VendrePK> {
}