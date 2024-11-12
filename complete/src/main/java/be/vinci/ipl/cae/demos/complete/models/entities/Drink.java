package be.vinci.ipl.cae.demos.complete.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "drinks")
@Data
@NoArgsConstructor
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    private String image;

    @Column(nullable = false)
    private double volume;

    @Column(nullable = false)
    private double price;

}
