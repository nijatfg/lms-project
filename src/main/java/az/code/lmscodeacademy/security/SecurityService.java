package az.code.lmscodeacademy.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final ModelMapper mapper;


    public JwtCredentials getCurrentJwtCredentials() {
        var securityContext = SecurityContextHolder.getContext();
        return Optional.of(securityContext.getAuthentication())
                .map(authentication -> mapper.map(authentication.getPrincipal(), JwtCredentials.class))
                .orElseThrow(RuntimeException::new);
    }
}
