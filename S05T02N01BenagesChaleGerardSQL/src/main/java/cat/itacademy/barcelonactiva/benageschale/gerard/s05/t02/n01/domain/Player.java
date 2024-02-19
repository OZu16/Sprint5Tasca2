package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Timestamp registerDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player", cascade = CascadeType.ALL)
    private List<Game> games;

    public Player() {
        //this.registerDate = new Timestamp(System.currentTimeMillis());
        //this.games = new ArrayList<>();
    }

    public Player(String name) {
        this.name = name;
        this.registerDate = new Timestamp(System.currentTimeMillis());
        //this.games = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
