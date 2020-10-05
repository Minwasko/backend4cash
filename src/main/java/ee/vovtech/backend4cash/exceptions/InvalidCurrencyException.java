package ee.vovtech.backend4cash.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends RuntimeException {

    public InvalidCurrencyException(String errorMessage) {
        super(errorMessage);
    }
}
