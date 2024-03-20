package az.code.lmscodeacademy.exception.password;

import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {

    public final ErrorCodes errorCode;

    public InvalidPasswordException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }
}
