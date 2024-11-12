package be.vinci.ipl.cae.demos.complete.services;

import be.vinci.ipl.cae.demos.complete.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

}
