package be.vinci.ipl.cae.demos.complete.controllers;

import be.vinci.ipl.cae.demos.complete.models.entities.Ingredient;
import be.vinci.ipl.cae.demos.complete.models.entities.Pizza;
import be.vinci.ipl.cae.demos.complete.services.IngredientService;
import be.vinci.ipl.cae.demos.complete.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final UserService userService;

    public IngredientController(IngredientService ingredientService, UserService userService) {
        this.ingredientService = ingredientService;
        this.userService = userService;
    }

    @GetMapping("/")
    public Iterable<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
    }

    @GetMapping("/{ingredient}/pizzas")
    public Iterable<Pizza> getPizzas(@PathVariable String ingredient) {
        return ingredientService.getPizzas(ingredient);
    }

    @PostMapping("/{ingredient}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addIngredient(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable String ingredient
    ) {
        if (userService.invalidJwtToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        if (userService.isNotAdmin(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        }

        if (!ingredientService.verifyNewIngredient(ingredient)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ingredient");
        }

        if (ingredientService.exists(ingredient)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ingredient already exists");
        }

        ingredientService.addIngredient(ingredient);
    }

}
