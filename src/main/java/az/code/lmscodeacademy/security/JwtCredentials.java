package az.code.lmscodeacademy.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtCredentials {

    List<String> authority;
    String sub;
    Long iat;
    Long exp;
    String email;
    String username;
}
