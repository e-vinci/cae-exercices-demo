package be.vinci.ipl.cae.demos.complete.controllers;

import be.vinci.ipl.cae.demos.complete.services.DrinkService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

}
