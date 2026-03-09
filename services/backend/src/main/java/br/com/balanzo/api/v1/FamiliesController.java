package br.com.balanzo.api.v1;

import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/families")
public class FamiliesController {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public FamiliesController(FamilyRepository familyRepository, FamilyMemberRepository familyMemberRepository) {
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
    }

    @GetMapping
    public ResponseEntity<List<FamilySummary>> list(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.ok(List.of());
        }

        var members = familyMemberRepository.findByUserIdAndStatus(userId, FamilyMemberStatus.active);
        var familyIds = members.stream()
                .map(m -> m.getFamily().getId())
                .toList();

        var families = familyRepository.findAllById(familyIds);
        var summaries = families.stream()
                .map(this::toSummary)
                .toList();

        return ResponseEntity.ok(summaries);
    }

    private UUID userIdFrom(Principal principal) {
        if (principal instanceof Jwt jwt) {
            var sub = jwt.getSubject();
            if (sub != null) {
                try {
                    return UUID.fromString(sub);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return null;
    }

    private FamilySummary toSummary(Family f) {
        return new FamilySummary(f.getId(), f.getName());
    }

    public record FamilySummary(UUID id, String name) {}
}
