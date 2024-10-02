package fr.mb.brewshop.repositories;

import fr.mb.brewshop.entities.ArticleEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class ArticleRepository implements PanacheRepositoryBase<ArticleEntity, Integer> {
    public PanacheQuery<ArticleEntity> buildArticleQuery(String nom, BigDecimal prixMin, BigDecimal prixMax, Integer volume, String nomMarque, String nomCouleur, String nomTypeBiere) {
        String query = "1=1";
        Map<String, Object> params = new HashMap<>();

        if (nom != null && !nom.isBlank()) {
            query += " and lower(nomArticle) like lower(:nom)";
            params.put("nom", "%" + nom + "%");
        }
        if (prixMin != null) {
            query += " and prixAchat >= :prixMin";
            params.put("prixMin", prixMin);
        }
        if (prixMax != null) {
            query += " and prixAchat <= :prixMax";
            params.put("prixMax", prixMax);
        }
        if (volume != null) {
            query += " and volume = :volume";
            params.put("volume", volume);
        }
        if (nomMarque != null && !nomMarque.isBlank()) {
            query += " and lower(marque.nomMarque) like lower(:nomMarque)";
            params.put("nomMarque", "%" + nomMarque + "%");
        }
        if (nomCouleur != null && !nomCouleur.isBlank()) {
            query += " and lower(couleur.nomCouleur) like lower(:nomCouleur)";
            params.put("nomCouleur", "%" + nomCouleur + "%");
        }
        if (nomTypeBiere != null && !nomTypeBiere.isBlank()) {
            query += " and lower(typeBiere.nomType) like lower(:nomTypeBiere)";
            params.put("nomTypeBiere", "%" + nomTypeBiere + "%");
        }

        return find(query, params);
    }
}