package az.code.lmscodeacademy.dto.request.signup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    String firstName;
    String lastName;
    String email;

    String username;

    String password;
}
