package az.code.lmscodeacademy.exception;

import lombok.Getter;

@Getter
public class EmailExistException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;

    public EmailExistException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
