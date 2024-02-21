package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions;

public class PlayerWithoutGames extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PlayerWithoutGames(String msg){
        super(msg);
    }
}
