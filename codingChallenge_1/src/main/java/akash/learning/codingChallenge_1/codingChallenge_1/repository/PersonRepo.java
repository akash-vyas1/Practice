package akash.learning.codingChallenge_1.codingChallenge_1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Person;

@Repository // optional// try without this also
public interface PersonRepo extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String email);
}
