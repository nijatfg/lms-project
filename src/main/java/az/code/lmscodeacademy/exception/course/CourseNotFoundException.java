package az.code.lmscodeacademy.exception.course;

import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import lombok.Getter;

@Getter
public class CourseNotFoundException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;


    public CourseNotFoundException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
