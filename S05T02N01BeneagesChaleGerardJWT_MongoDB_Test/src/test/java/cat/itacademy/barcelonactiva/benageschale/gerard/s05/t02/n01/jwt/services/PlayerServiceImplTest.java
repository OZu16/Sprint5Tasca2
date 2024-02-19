package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.services;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Game;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain.Player;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.repository.PlayerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl(playerRepository);

    private Player playerTest1;
    private Player playerTest2;
    private Game gametest1;
    private Game gametest2;
    private Game gametest3;
    private Game gametest4;
    private PlayerDTO playerDTOTest1;
    private PlayerDTO playerDTOTest2;

    private List<Player> playerList;
    private List<PlayerDTO> playerDTOList;

    @BeforeEach
    void setUp() {

        gametest1 = new Game();
        gametest1.setDice1(6);
        gametest1.setDice2(1);
        gametest2 = new Game();
        gametest2.setDice1(3);
        gametest2.setDice2(2);
        gametest3 = new Game();
        gametest3.setDice1(6);
        gametest3.setDice2(5);
        gametest4 = new Game();
        gametest4.setDice1(2);
        gametest4.setDice2(5);

        playerTest1 = new Player("Manolo");
        playerTest1.setId("65d2a366a56a414e7f1616ba");
        playerTest1.getGames().add(gametest1);
        playerTest1.getGames().add(gametest4);

        playerTest2 = new Player("Manel");
        playerTest2.setId("65d2a366a56a414e7f1616bb");
        playerTest2.getGames().add(gametest2);
        playerTest2.getGames().add(gametest3);

        playerList = new ArrayList<>();
        playerList.add(playerTest1);
        playerList.add(playerTest2);

        playerDTOTest1 = new PlayerDTO("65d2a366a56a414e7f1616bc", "Marc", 99 );
        playerDTOTest2 = new PlayerDTO("65d2a366a56a414e7f1616bd", "Gerard", 1);

        playerDTOList = new ArrayList<>();
        playerDTOList.add(playerDTOTest1);
        playerDTOList.add(playerDTOTest2);

    }



    @Test
    void deletePlayerGamesTest() {
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616ba")).thenReturn(Optional.ofNullable(playerTest1));

        playerService.deletePlayerGames("65d2a366a56a414e7f1616ba");

        assertTrue(playerTest1.getGames().isEmpty());
    }

    @Test
    void findAllPlayersTest() {
        List<Player> players =new ArrayList<>();
        players.add(playerTest1);
        players.add(playerTest2);

        Mockito.when(playerRepository.findAll()).thenReturn(players);

        List<Player> listTemp = playerService.findAllPlayers();
        assertTrue(new ReflectionEquals(players).matches(listTemp));
    }

    @Test
    void playerConverterTest() {

        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616ba")).thenReturn(Optional.ofNullable(playerTest1));
        PlayerDTO playerDTO = playerService.playerConverter("65d2a366a56a414e7f1616ba");

        PlayerDTO playerDTOTest = new PlayerDTO("65d2a366a56a414e7f1616ba", "Manolo", 100);

        assertTrue(new ReflectionEquals(playerDTO).matches(playerDTOTest));
    }

    @Test
    void winnerPlayerTest() {

        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616ba")).thenReturn(Optional.ofNullable(playerTest1));
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616bb")).thenReturn(Optional.ofNullable(playerTest2));
        PlayerDTO expected = new PlayerDTO("65d2a366a56a414e7f1616ba", "Manolo", 100);
        PlayerDTO winner = playerService.winnerPlayer();
        assertTrue(new ReflectionEquals(expected).matches(winner));

    }

    @Test
    void loserPlayerTest() {
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616ba")).thenReturn(Optional.ofNullable(playerTest1));
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616bb")).thenReturn(Optional.ofNullable(playerTest2));
        PlayerDTO expected = new PlayerDTO("65d2a366a56a414e7f1616bb", "Manel", 0);
        PlayerDTO loser = playerService.loserPlayer();

        assertTrue(new ReflectionEquals(expected).matches(loser));
    }

    @Test
    void findAllGamesTest() {
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        List<Game> expected = new ArrayList<>();
        expected.add(gametest1);
        expected.add(gametest2);
        expected.add(gametest3);
        expected.add(gametest4);
        List<?> list = playerService.findAllGames();
        assertTrue(new ReflectionEquals(expected).matches(list));
    }

    @Test
    void totalWinPercentTest() {

        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616ba")).thenReturn(Optional.ofNullable(playerTest1));
        Mockito.when(playerRepository.findById("65d2a366a56a414e7f1616bb")).thenReturn(Optional.ofNullable(playerTest2));
        double expected = 50.0;
        double result = playerService.totalWinPercent();
        System.out.println(result);
        assertTrue(new ReflectionEquals(expected).matches(result));

    }

    @RepeatedTest(50)
    void playGameTest() {

        Player playerTest = new Player("Nuno");
        playerTest.getGames().add(new Game());
        int dice1 = playerTest.getGames().get(0).getDice1();
        int dice2 = playerTest.getGames().get(0).getDice2();
        System.out.println(dice2);
        assertTrue(dice1 >= 1 && dice1 <= 6);
        assertTrue(dice2 >= 1 && dice2 <= 6);

    }

    @Test
    void savePlayerTest() {
        Player playerJavi = new Player("Javi");
        playerJavi.setId("65d2a366a56a414e7f1616be");
        Player playerANONYMOUS = new Player("");
        playerANONYMOUS.setId("65d2a366a56a414e7f1616bf");
        Player playerRepeated = new Player("Manolo");
        playerRepeated.setId("65d2a366a56a414e7f1616bf");

        Mockito.when(playerRepository.findAll()).thenReturn(playerList);

        System.out.println(playerService.savePlayer(playerJavi));
        System.out.println(playerService.savePlayer(playerANONYMOUS));
        System.out.println(playerService.savePlayer(playerRepeated));
    }
}