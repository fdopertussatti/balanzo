package br.com.balanzo.infrastructure.persistence.planejamento;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Goal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    List<Goal> findByOwnerScopeAndOwnerUserId(OwnerScope scope, UUID userId);

    List<Goal> findByOwnerScopeAndOwnerFamilyId(OwnerScope scope, UUID familyId);
}
