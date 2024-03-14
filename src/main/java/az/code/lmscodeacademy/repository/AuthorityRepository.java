package az.code.lmscodeacademy.repository;

import az.code.lmscodeacademy.entity.Authority;
import az.code.lmscodeacademy.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(UserAuthority userAuthority);
}
