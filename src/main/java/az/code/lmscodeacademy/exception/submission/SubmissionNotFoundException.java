package az.code.lmscodeacademy.exception.submission;

import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import lombok.Getter;

@Getter
public class SubmissionNotFoundException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;


    public SubmissionNotFoundException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
