package br.com.balanzo.infrastructure.persistence.planejamento;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Budget;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    List<Budget> findByOwnerScopeAndOwnerUserId(OwnerScope scope, UUID userId);

    List<Budget> findByOwnerScopeAndOwnerFamilyId(OwnerScope scope, UUID familyId);
}
