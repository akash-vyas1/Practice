package akash.learning.codingChallenge_1.codingChallenge_1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}

// public class RoleRepo {
// }