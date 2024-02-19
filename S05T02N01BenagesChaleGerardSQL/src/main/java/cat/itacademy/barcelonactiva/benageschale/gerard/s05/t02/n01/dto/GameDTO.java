package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto;


import java.util.Objects;

public class GameDTO {

    private Long id;
    private Long dice1;
    private Long dice2;
    private Long sumDices;
    private Long idPlayer;

    public GameDTO() {
    }

    public GameDTO(Long id, Long dice1, Long dice2, Long idPlayer) {
        this.id = id;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.sumDices = dice1 + dice2;
        this.idPlayer = idPlayer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDice1() {
        return dice1;
    }

    public void setDice1(Long dice1) {
        this.dice1 = dice1;
    }

    public Long getDice2() {
        return dice2;
    }

    public void setDice2(Long dice2) {
        this.dice2 = dice2;
    }

    public Long getSumDices() {
        return sumDices;
    }

    public void setSumDices(Long sumDices) {
        this.sumDices = sumDices;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

}
