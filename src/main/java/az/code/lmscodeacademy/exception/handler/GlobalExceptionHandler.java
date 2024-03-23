package az.code.lmscodeacademy.exception.handler;

import az.code.lmscodeacademy.exception.assignment.AssignmentNotFoundException;
import az.code.lmscodeacademy.exception.group.GroupNotFoundException;
import az.code.lmscodeacademy.exception.lesson.LessonNotFoundException;
import az.code.lmscodeacademy.exception.password.InvalidPasswordException;
import az.code.lmscodeacademy.exception.users.UserNameExistException;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.exception.course.CourseNotFoundException;
import az.code.lmscodeacademy.exception.email.EmailExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailExistException(EmailExistException ex,
                                                                      WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Email already exist")
                .build());
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameExistException(UserNameExistException ex,
                                                                         WebRequest req) {
        var lang = req.getHeader(ACCEPT_LANGUAGE) == null ? "en" : req.getHeader(ACCEPT_LANGUAGE);
        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Username already exist")
                .build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                           WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Invalid password")
                .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex,
                                                                        WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("User not found")
                .build());
    }

    @ExceptionHandler(LessonNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleLessonNotFoundException(LessonNotFoundException ex,
                                                                        WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Lesson not found")
                .build());
    }

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAssignmentNotFoundException(AssignmentNotFoundException ex,
                                                                          WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Assignment not found")
                .build());
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCourseNotFoundException(CourseNotFoundException ex,
                                                                        WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Course not found")
                .build());
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGroupNotFoundException(GroupNotFoundException ex,
                                                                          WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Group not found")
                .build());
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
//                                                                                  WebRequest req) {
//        ex.printStackTrace();
//        ErrorResponseDto response = ErrorResponseDto.builder()
//                .status(400)
//                .title("Exception")
//                .details("Validation Error")
//                .build();
//
//        ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .forEach(error -> {
//                    Map<String, String> data = response.getData();
//                    data.put(error.getField(), "password must be 6 or more alphanumeric characters.");
//                });
//        return ResponseEntity.status(400).body(response);
//    }
}
