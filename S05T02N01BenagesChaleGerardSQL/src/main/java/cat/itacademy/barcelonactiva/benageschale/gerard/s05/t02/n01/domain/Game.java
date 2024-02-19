package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dice1")
    private Long dice1;
    @Column(name = "dice2")
    private Long dice2;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private Player player;

    public Game() {
        this.dice1 = (long) (Math.random()*6) + 1;
        this.dice2 = (long) (Math.random()*6) + 1;
    }


    public Long getId() {
        return id;
    }

    public Long getDice1() {
        return dice1;
    }

    public Long getDice2() {
        return dice2;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDice1(Long dice1) {
        this.dice1 = dice1;
    }

    public void setDice2(Long dice2) {
        this.dice2 = dice2;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", dice1=" + dice1 +
                ", dice2=" + dice2 +
                ", player=" + player +
                '}';
    }
}
