package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.repository;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
