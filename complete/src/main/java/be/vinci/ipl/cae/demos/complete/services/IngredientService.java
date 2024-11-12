package be.vinci.ipl.cae.demos.complete.services;

import be.vinci.ipl.cae.demos.complete.models.entities.Ingredient;
import be.vinci.ipl.cae.demos.complete.models.entities.Pizza;
import be.vinci.ipl.cae.demos.complete.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Iterable<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public Iterable<Pizza> getPizzas(String ingredient) {
        return ingredientRepository.findById(ingredient).map(Ingredient::getPizzas).orElse(null);
    }

    public boolean exists(String ingredient) {
        return ingredientRepository.existsById(ingredient);
    }

    public boolean verifyNewIngredient(String ingredient) {
        return ingredient != null && !ingredient.isEmpty();
    }

    public void addIngredient(String ingredient) {
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(ingredient);
        ingredientRepository.save(newIngredient);
    }

    public Set<Ingredient> getIngredients(List<String> ingredients) {
        Set<String> seen = new HashSet<>();
        Set<Ingredient> result = new HashSet<>();

        for (String ingredient : ingredients) {
            if (seen.contains(ingredient)) return null;
            Optional<Ingredient> value = ingredientRepository.findById(ingredient);
            if (value.isEmpty()) return null;
            seen.add(ingredient);
            result.add(value.get());
        }

        return result;
    }

}
