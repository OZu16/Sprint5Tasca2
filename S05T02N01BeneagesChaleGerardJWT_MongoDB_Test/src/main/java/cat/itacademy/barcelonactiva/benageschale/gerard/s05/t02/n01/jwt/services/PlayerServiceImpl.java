package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Game;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
public class PlayerServiceImpl implements  PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public String savePlayer(Player player) {
        Boolean copy = playerRepository.findAll().stream()
                .filter(p -> p.getName().equals(player.getName()))
                .findFirst()
                .map(p -> true)
                .orElse(false);

        if(copy){
            return "nom existent";
        }
        if(player.getName().replaceAll(" ", "").isEmpty()){
            player.setName("ANONYMOUS");
        }
        player.setRegisterDate(LocalDateTime.now());
        playerRepository.save(player);
        return "Jugador :" + player.getName() + " amb ID: " +  player.getId() + " creat amb exit!";
    }


    @Override
    public String playGame(String idPlayer) {

        Player playerTemp = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);
        Game game = new Game();
        game.setPlayerId(idPlayer);
        playerTemp.getGames().add(game);
        playerRepository.save(playerTemp);
        if (game.getDice1() + game.getDice2() == 7) {
            return "dau 1: " + game.getDice1() + " + dau 2: " + game.getDice2() + " = 7! Has guanyat!!";
        }
        return "dau 1: " + game.getDice1() + " + dau 2: " + game.getDice2() + " = " + (game.getDice1()+game.getDice2()) + " Has perdut....";
    }

    @Override
    public Player findPlayer(String idPlayer) {
        return playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);
    }

    @Override
    public String updatePlayer(Player updatePlayer, String idPlayer) {

        if(updatePlayer!=null) {
            Player playerTemp = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);
            Boolean copy = playerRepository.findAll().stream()
                    .filter(p -> p.getName().equals(updatePlayer.getName()))
                    .findFirst()
                    .map(p -> true)
                    .orElse(false);
            if (copy) {
                return "nom existent";
            }
            if (updatePlayer.getName().replaceAll(" ", "").isEmpty()) {
                updatePlayer.setName("ANONYMOUS");
            }
            playerTemp.setName(updatePlayer.getName());
            playerRepository.save(playerTemp);
            return "Canvis realitzats amb èxit";
        }
        return "Null";
    }

    @Override
    public void deletePlayer(String idPlayer) {
        Player playerTemp = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);
        playerRepository.deleteById(idPlayer);
    }

    @Override
    public void deletePlayerGames(String idPlayer) {
        Player playerTemp = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);
        playerTemp.getGames().clear();
        playerRepository.save(playerTemp);
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public PlayerDTO playerConverter(String idPlayer) {
        /*Player player = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);

        double wins = (double) player.getGames().stream()
                .filter(fl -> fl.getDice1() + fl.getDice2() == 7)
                .count();

        return new PlayerDTO(player.getId(), player.getName(), (wins/player.getGames().size())*100);*/
        Player player = playerRepository.findById(idPlayer).orElseThrow(PlayerNotFoundException::new);

        long wins = player.getGames().stream()
                .filter(game -> game.getDice1() + game.getDice2() == 7)
                .count();

        double winPercentage = 0.0;
        if (!player.getGames().isEmpty()) {
            winPercentage = ((double) wins / player.getGames().size()) * 100;
        }

        return new PlayerDTO(player.getId(), player.getName(), winPercentage);
    }

    @Override
    public PlayerDTO winnerPlayer() {

        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = findAllPlayers().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerConverter(p.getId())));

        return dtoPlayers.stream()
                .max(Comparator.comparingDouble(PlayerDTO::getPercentWin))
                .orElse(null);
    }

    @Override
    public PlayerDTO loserPlayer() {

        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = findAllPlayers().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerConverter(p.getId())));

        return dtoPlayers.stream()
                .min(Comparator.comparingDouble(PlayerDTO::getPercentWin))
                .orElse(null);
    }

    @Override
    public List<?> findAllGames() {
        List<Game> allGames = new ArrayList<>();
        playerRepository.findAll().forEach(p -> {
            if (!p.getGames().isEmpty()) {
                allGames.addAll(p.getGames());
            }
        });
        return allGames;
    }

    @Override
    public Double totalWinPercent() {
        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = playerRepository.findAll().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerConverter(p.getId())));


        double winPercent = dtoPlayers.stream()
                .map(PlayerDTO::getPercentWin)
                .mapToDouble(Double::doubleValue)
                .sum();

        return winPercent/(double) dtoPlayers.size();
    }
}