package be.vinci.ipl.cae.demos.complete.repositories;

import be.vinci.ipl.cae.demos.complete.models.entities.Drink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, UUID> {
    boolean existsByTitle(String title);

    Drink findByTitle(String title);

    Iterable<Drink> findAllByPriceLessThanEqual(double price);
}
