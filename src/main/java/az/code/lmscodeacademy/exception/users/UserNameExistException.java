package az.code.lmscodeacademy.exception.users;

import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import lombok.Getter;

@Getter
public class UserNameExistException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;

    public UserNameExistException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
