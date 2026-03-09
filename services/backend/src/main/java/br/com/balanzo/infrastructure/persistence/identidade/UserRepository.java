package br.com.balanzo.infrastructure.persistence.identidade;

import br.com.balanzo.domain.identidade.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
