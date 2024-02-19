package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class Game {

    @Id
    private ObjectId id;
    private int dice1;
    private int dice2;
    private String playerId;

    public Game() {
        this.id = new ObjectId();
        this.dice1 = (int) (Math.random()*6) + 1;
        this.dice2 = (int) (Math.random()*6) + 1;
    }


    public ObjectId getId() {
        return id;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
