package az.code.lmscodeacademy.dto.response.user;

import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.UserAuthority;
import az.code.lmscodeacademy.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<Authority> authorities;
    private GroupResponse group;
    private boolean passwordChanged;
}
