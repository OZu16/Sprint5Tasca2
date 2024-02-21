package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.controllers;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/dice_game")
public class DiceGameController {

    @Autowired
    private PlayerService playerService;


    @PostMapping(value = "/save_player")
    public ResponseEntity<String> savePlayer(@RequestBody Player player) {
        return new ResponseEntity<>(playerService.savePlayer(player), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete_player/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") String id) {
        playerService.deletePlayer(id);
        return new ResponseEntity<>("Jugador eliminat amb èxit", HttpStatus.GONE);
    }

    @DeleteMapping(value = "/delete_games/{id}/player")
    public ResponseEntity<String> deleteGames(@PathVariable("id") String id){
        playerService.deletePlayerGames(id);
        return new ResponseEntity<>("Partides eliminades amb èxit", HttpStatus.GONE);
    }

    @PutMapping(value = "/change_name/{id}")
    public ResponseEntity<String> updatePlayerName(@PathVariable("id")String id, @RequestBody Player updatePlayer) {
        return new ResponseEntity<>(playerService.updatePlayer(updatePlayer, id), HttpStatus.ACCEPTED);
    };


    @PostMapping(value = "/play_game/{id}")
    public ResponseEntity<String> playGame(@PathVariable("id") String id){
        return new ResponseEntity<>(playerService.playGame(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get_player_games/{id}")
    public ResponseEntity<Player> findPlayerGames(@PathVariable("id") String id) {
        return new ResponseEntity<>(playerService.findPlayerGames(id), HttpStatus.OK);
    }

    @GetMapping("/get_player/{id}/win_percent")
    public ResponseEntity<PlayerDTO> findPlayerDTO(@PathVariable("id") String id) {
        return new ResponseEntity<>(playerService.playerConverter(id), HttpStatus.OK);
    }

    @GetMapping(value = "/get_players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        playerService.findAllPlayers().forEach(p -> dtoPlayers.add(playerService.playerConverter(p.getId())));
        return new ResponseEntity<>(dtoPlayers, HttpStatus.OK);
    }

    @GetMapping(value = "/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner(){
        return new ResponseEntity<>(playerService.winnerPlayer(), HttpStatus.OK);
    }

    @GetMapping(value = "/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser(){
        return new ResponseEntity<>(playerService.loserPlayer(), HttpStatus.OK);
    }

    @GetMapping(value = "/get_games")
    public ResponseEntity<List<?>> getAllGames(){
        return new ResponseEntity<>(playerService.findAllGames(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_player/{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable("id") String id){
        return new ResponseEntity<>(playerService.playerConverter(id), HttpStatus.OK);
    }

    @GetMapping(value = "/ranking")
    public ResponseEntity<String> getAllPlayersWinPercent_mean(){
        return new ResponseEntity<>("Percentatge total de victòries: " + playerService.totalWinPercent() + "%", HttpStatus.OK);
    }
}
