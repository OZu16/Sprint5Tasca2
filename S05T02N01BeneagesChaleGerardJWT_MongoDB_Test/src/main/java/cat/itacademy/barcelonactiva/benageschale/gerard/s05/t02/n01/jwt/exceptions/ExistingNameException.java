package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions;

public class ExistingNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExistingNameException(String msg) {
        super(msg);
    }
}
