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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalsController {

    private final GoalRepository goalRepository;
    private final CreateGoal createGoal;

    public GoalsController(GoalRepository goalRepository, CreateGoal createGoal) {
        this.goalRepository = goalRepository;
        this.createGoal = createGoal;
    }

    @GetMapping
    public ResponseEntity<List<GoalSummary>> list(Principal principal,
                                                  @RequestParam(required = false) UUID familyId) {
        UUID userId = requireUserId(principal);
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
        UUID userId = requireUserId(principal);
        Goal g = createGoal.run(userId, req.name(), req.targetAmount(), req.currency(),
                req.targetDate(), req.ownerScope(), req.familyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(g));
    }

    private UUID requireUserId(Principal principal) {
        if (principal instanceof Jwt jwt && jwt.getSubject() != null) {
            try {
                return UUID.fromString(jwt.getSubject());
            } catch (IllegalArgumentException ignored) {}
        }
        throw new IllegalArgumentException("Authentication required");
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
