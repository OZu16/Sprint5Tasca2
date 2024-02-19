package cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.exceptions;

import cat.itacademy.barcelonactiva.benageschale.gerard.s05.t02.n01.exceptions.dtoError.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> existingNameException(ExistingNameException exception){
        ErrorMessage message= new ErrorMessage(HttpStatus.IM_USED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.IM_USED).body(message);
    }

}
