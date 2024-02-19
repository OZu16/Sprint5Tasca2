package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.services;


import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.exceptions.NotFoundException;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements  PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    //private GameService gameService;

    @Override
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findPlayer(long id) throws NotFoundException {
        return playerRepository.findById(id) .orElseThrow(() -> new NotFoundException("Player no existent"));
    }

    @Override
    public void deletePlayer(long id) throws NotFoundException {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            playerRepository.deleteById(id);
        } else {
            throw new NotFoundException("Jugador no existent");
        }
    }

    @Override
    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public PlayerDTO playerConverter(Player player) {
        double wins = (double) player.getGames().stream()
                .filter(fl -> fl.getDice1() + fl.getDice2() == 7)
                .count();
        return new PlayerDTO(player.getId(), player.getName(), (wins/player.getGames().size())*100);
    }


}
