package az.code.lmscodeacademy.dto.response.jwt;

import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.entity.authority.Authority;
import az.code.lmscodeacademy.entity.enums.MessageStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private Long id;
    String jwt;
    private List<Authority> authorities;
    private GroupResponse group;
    private MessageStatus status;
}
