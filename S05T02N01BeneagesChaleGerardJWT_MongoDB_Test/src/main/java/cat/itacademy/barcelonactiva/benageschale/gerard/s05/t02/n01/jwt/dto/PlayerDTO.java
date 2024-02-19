package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.dto;

public class PlayerDTO {

    private String id;
    private String name;
    private double percentWin;

    public PlayerDTO() {
    }

    public PlayerDTO(String id, String name, double percentWin) {
        this.id = id;
        this.name = name;
        this.percentWin = percentWin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
