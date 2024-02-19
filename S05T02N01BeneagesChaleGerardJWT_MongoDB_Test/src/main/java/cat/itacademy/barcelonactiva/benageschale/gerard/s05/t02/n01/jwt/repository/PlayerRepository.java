package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.repository;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}
