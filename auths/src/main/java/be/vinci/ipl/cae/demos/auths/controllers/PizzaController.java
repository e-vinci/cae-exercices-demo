package be.vinci.ipl.cae.demos.auths.controllers;

import be.vinci.ipl.cae.demos.auths.controllers.utils.Authorize;
import be.vinci.ipl.cae.demos.auths.models.dtos.NewPizza;
import be.vinci.ipl.cae.demos.auths.models.entities.Pizza;
import be.vinci.ipl.cae.demos.auths.services.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping({"", "/"})
    public Iterable<Pizza> getPizzas(@RequestParam(required = false) String order) {
        return pizzaService.readAllPizzas(order);
    }

    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable long id) {
        Pizza pizza = pizzaService.readPizzaById(id);
        if (pizza == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return pizza;
    }

    @Authorize(admin = true)
    @PostMapping({"", "/"})
    public Pizza addPizza(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody NewPizza newPizza) {
        System.out.println("in controller");
        if (newPizza == null ||
                newPizza.getTitle() == null ||
                newPizza.getTitle().isBlank() ||
                newPizza.getContent() == null ||
                newPizza.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return pizzaService.createPizza(newPizza);
    }

    @Authorize(admin = true)
    @DeleteMapping("/{id}")
    public Pizza deletePizza(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable long id) {
        Pizza pizza = pizzaService.deletePizza(id);
        if (pizza == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return pizza;
    }

    @Authorize(admin = true)
    @PatchMapping("/{id}")
    public Pizza updatePizza(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable long id, @RequestBody NewPizza newPizza) {
        if (newPizza == null ||
                (newPizza.getTitle() != null && newPizza.getTitle().isBlank()) ||
                (newPizza.getContent() != null && newPizza.getContent().isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Pizza pizza = pizzaService.updatePizza(id, newPizza);
        if (pizza == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return pizza;
    }

}
