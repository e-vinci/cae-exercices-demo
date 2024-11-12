package be.vinci.ipl.cae.demos.complete.repositories;

import be.vinci.ipl.cae.demos.complete.models.entities.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, UUID> {
    boolean existsByTitle(String title);

    Pizza findByTitle(String title);
}
