package br.com.balanzo.api.v1;

import br.com.balanzo.application.planejamento.CreateGoal;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.planejamento.entity.Goal;
import br.com.balanzo.infrastructure.persistence.planejamento.GoalRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.balanzo.common.exception.DomainException;
import br.com.balanzo.common.security.CurrentUserResolver;
import br.com.balanzo.security.authorization.FamilyScopeAccess;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalsController {

    private final GoalRepository goalRepository;
    private final CreateGoal createGoal;
    private final CurrentUserResolver currentUser;
    private final FamilyScopeAccess familyScopeAccess;

    public GoalsController(GoalRepository goalRepository, CreateGoal createGoal,
                           CurrentUserResolver currentUser, FamilyScopeAccess familyScopeAccess) {
        this.goalRepository = goalRepository;
        this.createGoal = createGoal;
        this.currentUser = currentUser;
        this.familyScopeAccess = familyScopeAccess;
    }

    @GetMapping
    public ResponseEntity<List<GoalSummary>> list(Principal principal,
                                                  @RequestParam(required = false) UUID familyId) {
        UUID userId = currentUser.require(principal);
        if (familyId != null) {
            familyScopeAccess.requireMemberCanView(userId, familyId);
        }
        List<Goal> goals;
        if (familyId != null) {
            goals = goalRepository.findByOwnerScopeAndOwnerFamilyId(OwnerScope.family, familyId);
        } else {
            goals = goalRepository.findByOwnerScopeAndOwnerUserId(OwnerScope.user, userId);
        }
        return ResponseEntity.ok(goals.stream().map(this::toSummary).toList());
    }

    @PostMapping
    public ResponseEntity<GoalSummary> create(Principal principal, @Valid @RequestBody CreateGoalRequest req) {
        UUID userId = currentUser.require(principal);
        if (req.ownerScope() == OwnerScope.family && req.familyId() == null) {
            throw new DomainException("familyId is required when ownerScope is family");
        }
        if (req.ownerScope() == OwnerScope.family && req.familyId() != null) {
            familyScopeAccess.requireMemberCanEdit(userId, req.familyId());
        }
        Goal g = createGoal.run(userId, req.name(), req.targetAmount(), req.currency(),
                req.targetDate(), req.ownerScope(), req.familyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(g));
    }

    private GoalSummary toSummary(Goal g) {
        return new GoalSummary(g.getId(), g.getName(), g.getTargetAmount(), g.getCurrency(),
                g.getTargetDate(), g.getCurrentProgress());
    }

    public record GoalSummary(UUID id, String name, BigDecimal targetAmount, String currency,
                              LocalDate targetDate, BigDecimal currentProgress) {}

    public record CreateGoalRequest(
            @NotBlank String name,
            @NotNull BigDecimal targetAmount,
            String currency,
            LocalDate targetDate,
            @NotNull OwnerScope ownerScope,
            UUID familyId
    ) {}
}
