package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.repository;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Game;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameReposirory extends JpaRepository<Game, Long> {
    List<Game> findByPlayer(Player player);
}
