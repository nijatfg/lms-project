package az.code.lmscodeacademy.dto.response.user;

import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private List<Authority> authorities;
}
