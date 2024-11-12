package be.vinci.ipl.cae.demos.complete.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pizzas")
@Data
@NoArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private double price;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_name")
    )
    private Set<Ingredient> ingredients;

}
