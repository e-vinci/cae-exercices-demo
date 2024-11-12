package be.vinci.ipl.cae.demos.complete.repositories;

import be.vinci.ipl.cae.demos.complete.models.entities.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
