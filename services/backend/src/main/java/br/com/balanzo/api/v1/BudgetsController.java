package br.com.balanzo.api.v1;

import br.com.balanzo.application.planejamento.CreateBudget;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Budget;
import br.com.balanzo.infrastructure.persistence.planejamento.BudgetRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetsController {

    private final BudgetRepository budgetRepository;
    private final CreateBudget createBudget;

    public BudgetsController(BudgetRepository budgetRepository, CreateBudget createBudget) {
        this.budgetRepository = budgetRepository;
        this.createBudget = createBudget;
    }

    @GetMapping
    public ResponseEntity<List<BudgetSummary>> list(Principal principal,
                                                    @RequestParam(required = false) UUID familyId) {
        UUID userId = requireUserId(principal);
        List<Budget> budgets;
        if (familyId != null) {
            budgets = budgetRepository.findByOwnerScopeAndOwnerFamilyId(OwnerScope.family, familyId);
        } else {
            budgets = budgetRepository.findByOwnerScopeAndOwnerUserId(OwnerScope.user, userId);
        }
        return ResponseEntity.ok(budgets.stream().map(this::toSummary).toList());
    }

    @PostMapping
    public ResponseEntity<BudgetSummary> create(Principal principal, @Valid @RequestBody CreateBudgetRequest req) {
        UUID userId = requireUserId(principal);
        Budget b = createBudget.run(userId, req.ownerScope(), req.familyId(), req.categoryId(),
                req.periodStart(), req.periodEnd(), req.amount(), req.currency());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(b));
    }

    private UUID requireUserId(Principal principal) {
        if (principal instanceof Jwt jwt && jwt.getSubject() != null) {
            try {
                return UUID.fromString(jwt.getSubject());
            } catch (IllegalArgumentException ignored) {}
        }
        throw new IllegalArgumentException("Authentication required");
    }

    private BudgetSummary toSummary(Budget b) {
        return new BudgetSummary(b.getId(), b.getPeriodStart(), b.getPeriodEnd(),
                b.getAmount(), b.getCurrency(), b.getCategory() != null ? b.getCategory().getId() : null);
    }

    public record BudgetSummary(UUID id, LocalDate periodStart, LocalDate periodEnd,
                                BigDecimal amount, String currency, UUID categoryId) {}

    public record CreateBudgetRequest(
            @NotNull OwnerScope ownerScope,
            UUID familyId,
            UUID categoryId,
            @NotNull LocalDate periodStart,
            @NotNull LocalDate periodEnd,
            @NotNull BigDecimal amount,
            String currency
    ) {}
}
