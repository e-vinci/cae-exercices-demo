package be.vinci.ipl.cae.demos.complete.controllers;

import be.vinci.ipl.cae.demos.complete.models.dtos.NewPizza;
import be.vinci.ipl.cae.demos.complete.models.entities.Ingredient;
import be.vinci.ipl.cae.demos.complete.models.entities.Pizza;
import be.vinci.ipl.cae.demos.complete.services.IngredientService;
import be.vinci.ipl.cae.demos.complete.services.PizzaService;
import be.vinci.ipl.cae.demos.complete.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@RestController("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;
    private final UserService userService;
    private final IngredientService ingredientService;

    public PizzaController(PizzaService pizzaService, UserService userService, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.userService = userService;
        this.ingredientService = ingredientService;
    }

    @GetMapping({"", "/"})
    public Iterable<Pizza> getPizzas() {
        return pizzaService.getAllPizzas();
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza addPizza(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody NewPizza newPizza
    ) {
        if (userService.invalidJwtToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        if (pizzaService.invalidNewPizza(newPizza)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid new pizza");
        }

        if (pizzaService.titleExists(newPizza.getTitle())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Another pizza already has this title");
        }

        Set<Ingredient> ingredients = ingredientService.getIngredients(newPizza.getIngredients());

        if (ingredients == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown or duplicate ingredient");
        }

        return pizzaService.createPizza(newPizza.getTitle(), newPizza.getPrice(), ingredients);
    }

    @GetMapping("/{uuid}")
    public Pizza getPizza(@PathVariable UUID uuid) {
        Pizza found = pizzaService.findById(uuid);
        if (found == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pizza found for this id");
        }
        return found;
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePizza(@PathVariable String uuid) {

    }



}
