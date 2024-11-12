package be.vinci.ipl.cae.demos.complete.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
public class Ingredient {

    @Id
    private String name;

    @Column(nullable = false)
    private double defaultPrice;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Pizza> pizzas;

}
