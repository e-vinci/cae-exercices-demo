package be.vinci.ipl.cae.demos.auths.repositories;

import be.vinci.ipl.cae.demos.auths.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
