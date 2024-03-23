package az.code.lmscodeacademy.dto.request.user;

import az.code.lmscodeacademy.entity.enums.UserAuthority;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private UserAuthority authority;
}
