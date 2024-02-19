package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.controllers;


import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Game;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto.GameDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.exceptions.NotFoundException;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.repository.GameReposirory;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.services.GameService;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping(value = "/api/dice_game")
public class DiceGameController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/save_player")
    public ResponseEntity<String> savePlayer(@RequestBody Player player) {

        Boolean copy = playerService.findAllPlayers().stream()
                .filter(p -> p.getName().equals(player.getName()))
                .findFirst()
                .map(p -> true)
                .orElse(false);

        if(copy){
            return new ResponseEntity<>("Jugador existent", HttpStatus.IM_USED);
        }
        if(player.getName().replaceAll(" ", "").isEmpty()){
            player.setName("ANONYMOUS");
        }

        Player playerIn = new Player(player.getName());

            List<Game> games = new ArrayList<>();
            for (Game gameIn : player.getGames()) {
                Game game = new Game();
                game.setPlayer(playerIn);
                games.add(game);
            }
            playerIn.setGames(games);
        playerService.savePlayer(playerIn);
        return new ResponseEntity<>("Jugador afegit", HttpStatus.CREATED);
    }

    @PutMapping(value = "/change_name/{id}")
    public ResponseEntity<String> updatePlayerName(@PathVariable("id")Long id, @RequestBody Player updatePlayer) throws NotFoundException{

        Player player = playerService.findPlayer(id);

        if (updatePlayer != null) {
            player.setName(updatePlayer.getName());
            playerService.updatePlayer(player);
            return new ResponseEntity<>("Canvis realitzats amb èxit", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Sense canvis", HttpStatus.NOT_MODIFIED);

    };

    @DeleteMapping(value = "/delete_player/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") Long id) throws NotFoundException{
        playerService.deletePlayer(id);
        return new ResponseEntity<>("Jugador eliminat amb èxit", HttpStatus.GONE);
    }

    @DeleteMapping(value = "/delete_games/{id}/player")
    public ResponseEntity<String> deleteGames(@PathVariable("id") Long id) throws NotFoundException{

        playerService.findPlayer(id).getGames().forEach(g-> {
                            try {
                                gameService.deleteGame(g);
                            } catch (NotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });

        return new ResponseEntity<>("Partides eliminades amb èxit", HttpStatus.GONE);
    }

    @PostMapping(value = "/play_game")
    public ResponseEntity<String> playGame(@RequestParam("id") Long id) throws NotFoundException {

        Player playerTemp = playerService.findPlayer(id);
        Game game = new Game();
        long a = game.getDice1();
        long b = game.getDice2();
        game.setPlayer(playerTemp);
        playerTemp.getGames().add(game);
        playerService.savePlayer(playerTemp);

        if (a + b == 7) {
            return new ResponseEntity<>("dau 1: " + a + " + dau 2: " + b + " = 7! Has guanyat!!", HttpStatus.OK);
        }

        return new ResponseEntity<>("dau 1: " + a + " + dau 2: " + b + " = " + (a+b) + " Has perdut....", HttpStatus.OK);
    }


    @GetMapping(value = "/get_players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        playerService.findAllPlayers().forEach(p -> dtoPlayers.add(playerService.playerConverter(p)));
        return new ResponseEntity<>(dtoPlayers, HttpStatus.OK);
    }



    @GetMapping("/get_player/{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable("id") Long id) throws NotFoundException {

        PlayerDTO dtoPlayer = playerService.playerConverter(playerService.findPlayer(id));

        return new ResponseEntity<>(dtoPlayer, HttpStatus.OK);
    }

    @GetMapping(value = "/ranking")
    public ResponseEntity<Double> getAllPlayersWinPercent_mean(){

        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = playerService.findAllPlayers().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerService.playerConverter(p)));

        double winPercent = dtoPlayers.stream()
                .map(PlayerDTO::getPercentWin)
                .mapToDouble(Double::doubleValue)
                .sum();

        return new ResponseEntity<>(winPercent/dtoPlayers.size(), HttpStatus.OK);
    }

    @GetMapping(value = "/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner(){

        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = playerService.findAllPlayers().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerService.playerConverter(p)));

        PlayerDTO winner = dtoPlayers.stream()
                .filter(p-> p.getPercentWin() > 0)
                .max(Comparator.comparingDouble(PlayerDTO::getPercentWin))
                .orElse(null);

        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @GetMapping(value = "/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser(){

        List<PlayerDTO> dtoPlayers = new ArrayList<PlayerDTO>();
        List<Player> flPlayerList = playerService.findAllPlayers().stream()
                .filter(fl-> !fl.getGames().isEmpty())
                .toList();

        flPlayerList.forEach(p -> dtoPlayers.add(playerService.playerConverter(p)));

        PlayerDTO winner = dtoPlayers.stream()
                .filter(p-> p.getPercentWin() > 0)
                .min(Comparator.comparingDouble(PlayerDTO::getPercentWin))
                .orElse(null);

        return new ResponseEntity<>(winner, HttpStatus.OK);
    }


    @GetMapping(value = "/get_games")
    public ResponseEntity<List<GameDTO>> getAllGames(){

        List<GameDTO> dtoGames = new ArrayList<>();
        gameService.findAllGames().forEach(g -> dtoGames.add(gameService.gameConverter(g)));
        return new ResponseEntity<>(dtoGames, HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/get_player_games/{id}")
    public ResponseEntity<List<GameDTO>> getAllPlayerGames(@PathVariable("id") Long id){

        List<GameDTO> dtoGames = new ArrayList<>();
        List<GameDTO> dtoGamesFl = new ArrayList<>();
        gameService.findAllGames().forEach(g -> dtoGames.add(gameService.gameConverter(g)));
        dtoGames.stream()
                .filter(g-> g.getIdPlayer().equals(id))
                .forEach(dtoGamesFl::add);

        return new ResponseEntity<>(dtoGamesFl, HttpStatus.ACCEPTED);
    }


    /*@GetMapping("/getBlog/{id}")
    public String getBlog(@PathVariable(name = "id") String id) {
        System.out.println("Blog get called...");

        // fetch Blog
        Blog blogOut = blogRepository.getById(Integer.valueOf(id));
        System.out.println("\nBlog details :: \n" + blogOut);
        System.out.println("\nOwner details :: \n" + blogOut.getOwner());

        System.out.println("\nDone!!!");
        return "Blog fetched...";
    }*/


        /*@Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameReposirory gameReposirory;

    /*@GetMapping(value = "/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "/create_player")
    public String createPlayer(Model model){
        Player player = new Player();
        model.addAttribute("title", "Afegir jugador");
        model.addAttribute("player", player);
        return "addPlayer";
    }

    @PostMapping(value = "/save_player")
    public String addPlayer(@ModelAttribute Player player){

        playerService.savePlayer(player);
        return "redirect:/api/dice_game/play";
    }

    @GetMapping(value = "/play")
    public String playGame(){
        return "playGame";
    }
*/

        /*@PostMapping(value = "/play_game")
    public ResponseEntity<String> playGame(@RequestParam("id") Long id) throws NotFoundException {

        Player playerTemp = playerService.findPlayer(id);

        List<Game> games = new ArrayList<>();

        Game game = new Game();
        long a = game.getDice1();
        long b = game.getDice2();

        game.setPlayer(playerTemp);

        games.add(game);

        playerTemp.setGames(games);

        playerService.savePlayer(playerTemp);

        System.out.println(playerService.findPlayer(id).getGames().get(0).getDice1());

        for(int i = 0; i < playerService.findPlayer(id).getGames().size(); i++){
            System.out.println(i);
        }

        if (a + b == 7) {
            return new ResponseEntity<>("dau 1: " + a + " + dau 2: " + b + " = 7! Has guanyat!!", HttpStatus.OK);
        }

        return new ResponseEntity<>("dau 1: " + a + " + dau 2: " + b + " = " + (a+b) + " Has perdut....", HttpStatus.OK);
    }*/
}
