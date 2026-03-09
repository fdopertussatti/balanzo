package br.com.balanzo.infrastructure.persistence.financeiro;

import br.com.balanzo.domain.financeiro.entity.Account;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByOwnerId(UUID ownerId);

    List<Account> findByFamilyId(UUID familyId);
}
