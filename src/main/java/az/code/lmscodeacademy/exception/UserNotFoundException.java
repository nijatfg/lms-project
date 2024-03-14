package az.code.lmscodeacademy.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;


    public UserNotFoundException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
