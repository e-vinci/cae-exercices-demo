package be.vinci.ipl.cae.demos.auths.repositories;

import be.vinci.ipl.cae.demos.auths.models.entities.Drink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Long> {

    Iterable<Drink> findAllByPriceLessThanEqual(double price);

}
