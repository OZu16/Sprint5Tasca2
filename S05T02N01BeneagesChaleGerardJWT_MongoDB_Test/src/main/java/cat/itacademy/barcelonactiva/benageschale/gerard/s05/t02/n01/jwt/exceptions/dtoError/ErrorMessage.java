package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.jwt.exceptions.dtoError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}