package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions;

public class ExistingNameException extends Exception {

    public ExistingNameException() {
        super("Nom existent");
    }
}
