package br.com.balanzo.application.planejamento;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Goal;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.planejamento.GoalRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateGoal {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public CreateGoal(GoalRepository goalRepository, UserRepository userRepository,
                      FamilyRepository familyRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Transactional
    public Goal run(UUID userId, String name, BigDecimal targetAmount, String currency,
                    LocalDate targetDate, OwnerScope ownerScope, UUID familyId) {
        Goal g = new Goal();
        g.setOwnerScope(ownerScope);
        g.setName(name);
        g.setTargetAmount(targetAmount);
        g.setCurrency(currency != null ? currency : "BRL");
        g.setTargetDate(targetDate);

        if (ownerScope == OwnerScope.user) {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            g.setOwnerUser(owner);
        } else {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new IllegalArgumentException("Family not found: " + familyId));
            g.setOwnerFamily(family);
        }

        return goalRepository.save(g);
    }
}
