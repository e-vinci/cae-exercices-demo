package be.vinci.ipl.cae.demos.complete.services;

import be.vinci.ipl.cae.demos.complete.models.dtos.NewPizza;
import be.vinci.ipl.cae.demos.complete.models.entities.Ingredient;
import be.vinci.ipl.cae.demos.complete.models.entities.Pizza;
import be.vinci.ipl.cae.demos.complete.repositories.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public Iterable<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public boolean invalidNewPizza(NewPizza newPizza) {
        return newPizza.getTitle() == null ||
                newPizza.getTitle().isBlank() ||
                newPizza.getIngredients() == null ||
                newPizza.getIngredients().isEmpty();
    }

    public boolean titleExists(String title) {
        return pizzaRepository.existsByTitle(title);
    }

    public Pizza createPizza(String title, Set<Ingredient> ingredients) {
        Pizza pizza = new Pizza();
        pizza.setTitle(title);
        pizza.setIngredients(ingredients);
        return pizzaRepository.save(pizza);
    }

    public Pizza findById(UUID uuid) {
        return pizzaRepository.findById(uuid).orElse(null);
    }

}
