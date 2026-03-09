package br.com.balanzo.application.planejamento;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Budget;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.classificacao.CategoryRepository;
import br.com.balanzo.infrastructure.persistence.planejamento.BudgetRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateBudget {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final CategoryRepository categoryRepository;

    public CreateBudget(BudgetRepository br, UserRepository ur, FamilyRepository fr,
                        CategoryRepository cr) {
        this.budgetRepository = br;
        this.userRepository = ur;
        this.familyRepository = fr;
        this.categoryRepository = cr;
    }

    @Transactional
    public Budget run(UUID userId, OwnerScope ownerScope, UUID familyId, UUID categoryId,
                      LocalDate periodStart, LocalDate periodEnd, BigDecimal amount, String currency) {
        Budget b = new Budget();
        b.setOwnerScope(ownerScope);
        b.setPeriodStart(periodStart);
        b.setPeriodEnd(periodEnd);
        b.setAmount(amount);
        b.setCurrency(currency != null ? currency : "BRL");

        if (ownerScope == OwnerScope.user) {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            b.setOwnerUser(owner);
        } else {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new IllegalArgumentException("Family not found: " + familyId));
            b.setOwnerFamily(family);
        }

        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(b::setCategory);
        }

        return budgetRepository.save(b);
    }
}
