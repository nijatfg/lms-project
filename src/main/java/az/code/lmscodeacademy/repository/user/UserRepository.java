package az.code.lmscodeacademy.repository.user;

import az.code.lmscodeacademy.entity.enums.MessageStatus;
import az.code.lmscodeacademy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String confirmationToken);

    List<User> findByGroupId(Long groupId);

    List<User> findAllByStatus(MessageStatus status);
    Optional<User> findByUsername(String username);

}
