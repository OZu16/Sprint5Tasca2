package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.services;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Game;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto.GameDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface GameService {

    public void saveGame(Game game);
    public void deleteGame(Game game) throws NotFoundException;
    public List<Game> findAllGames();
    public Optional<Game> findGame(long id);

    public GameDTO gameConverter(Game game);

}
