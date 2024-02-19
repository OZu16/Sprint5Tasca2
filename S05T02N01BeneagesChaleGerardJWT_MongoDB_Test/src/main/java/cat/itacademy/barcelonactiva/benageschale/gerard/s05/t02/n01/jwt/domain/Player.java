package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.domain;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "players")
public class Player {

    @Id
    private String id;
    private String name;
    private LocalDateTime registerDate;
    private List<Game> games = new ArrayList<>();

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
