package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    public String savePlayer(Player player);

    public String playGame(String idPlayer);

    public Player findPlayer(String idPlayer);

    public String updatePlayer(Player player, String idPlayer);

    public void deletePlayer(String idPlayer);

    public void deletePlayerGames(String idPlayer);

    public List<Player> findAllPlayers();

    public PlayerDTO playerConverter(String idPlayer);

    public PlayerDTO winnerPlayer();

    public PlayerDTO loserPlayer();

    public List<?> findAllGames();

    public Double totalWinPercent();

}
