package be.vinci.ipl.cae.demos.auths.controllers;

import be.vinci.ipl.cae.demos.auths.controllers.utils.Authorize;
import be.vinci.ipl.cae.demos.auths.models.dtos.NewDrink;
import be.vinci.ipl.cae.demos.auths.models.entities.Drink;
import be.vinci.ipl.cae.demos.auths.services.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping({"", "/"})
    public Iterable<Drink> getDrinks(@RequestParam(name = "budget-max", required = false) Double budgetMax) {
        if (budgetMax != null) return drinkService.readAllDrinksWithinBudget(budgetMax);
        return drinkService.readAllDrinks();
    }

    @GetMapping("/{id}")
    public Drink getDrink(@PathVariable(name = "id") long id) {
        Drink drink = drinkService.readOneDrink(id);
        if (drink == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return drink;
    }

    @Authorize(admin = true)
    @PostMapping({"", "/"})
    public Drink addDrink(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody NewDrink newDrink) {
        if (newDrink == null ||
                newDrink.getTitle() == null ||
                newDrink.getTitle().isBlank() ||
                newDrink.getImage() == null ||
                newDrink.getImage().isBlank() ||
                newDrink.getVolume() <= 0 ||
                newDrink.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return drinkService.createOneDrink(newDrink);
    }

    @Authorize(admin = true)
    @DeleteMapping("/{id}")
    public Drink deleteDrink(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable(name = "id") long id) {
        Drink drink = drinkService.deleteOneDrink(id);
        if (drink == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return drink;
    }

    @Authorize(admin = true)
    @PatchMapping("/{id}")
    public Drink updateDrink(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable(name = "id") long id, @RequestBody NewDrink newDrink) {
        if (newDrink == null ||
                (newDrink.getTitle() != null && newDrink.getTitle().isBlank()) ||
                (newDrink.getImage() != null && newDrink.getImage().isBlank()) ||
                newDrink.getVolume() < 0.0 ||
                newDrink.getPrice() < 0.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Drink drink = drinkService.updateOneDrink(id, newDrink);
        if (drink == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return drink;
    }

}
