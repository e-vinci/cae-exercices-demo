package be.vinci.ipl.cae.demos.complete.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewIngredient {
    private String name;
    private double defaultPrice;
}
