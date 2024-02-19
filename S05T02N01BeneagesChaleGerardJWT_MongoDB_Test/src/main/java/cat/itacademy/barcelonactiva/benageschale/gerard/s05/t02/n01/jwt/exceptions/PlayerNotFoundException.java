package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PlayerNotFoundException extends EntityNotFoundException {

    public PlayerNotFoundException() {
        super("Jugador no existent!");
    }
}