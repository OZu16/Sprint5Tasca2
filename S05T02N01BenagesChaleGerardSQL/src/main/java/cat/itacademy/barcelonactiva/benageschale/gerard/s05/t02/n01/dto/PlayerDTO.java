package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.dto;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.domain.Game;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {

    private Long id;
    private String name;
    private double percentWin;

    public PlayerDTO() {
    }

    public PlayerDTO(Long id, String name, double percentWin) {
        this.id = id;
        this.name = name;
        this.percentWin = percentWin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercentWin() {
        return percentWin;
    }

    public void setPercentWin(double percentWin) {
        this.percentWin = percentWin;
    }
}
