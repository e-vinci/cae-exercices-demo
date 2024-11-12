package be.vinci.ipl.cae.demos.complete.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NewPizza {
    private String title;
    private List<String> ingredients;
}
