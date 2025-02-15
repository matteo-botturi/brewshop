package fr.mb.brewshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TYPEBIERE", schema = "dbo", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_NOM_TYPE", columnNames = {"NOM_TYPE"})
})
public class TypeBiereEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TYPE", nullable = false)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @Column(name = "NOM_TYPE", nullable = false, length = 25)
    private String nomTypeBiere;

    @OneToMany(mappedBy = "typeBiere")
    private List<ArticleEntity> articles = new ArrayList<>();

    //Only for DataInitializer
    public TypeBiereEntity(String nomTypeBiere) {
        this.nomTypeBiere = nomTypeBiere;
    }
}