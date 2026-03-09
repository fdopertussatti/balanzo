package br.com.balanzo.api.v1;

import br.com.balanzo.application.familia.AddFamilyMember;
import br.com.balanzo.application.familia.CreateFamily;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.familia.entity.FamilyMember;
import br.com.balanzo.domain.familia.entity.FamilyMemberRole;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/families")
public class FamiliesController {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final CreateFamily createFamily;
    private final AddFamilyMember addFamilyMember;

    public FamiliesController(FamilyRepository familyRepository,
                              FamilyMemberRepository familyMemberRepository,
                              CreateFamily createFamily,
                              AddFamilyMember addFamilyMember) {
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.createFamily = createFamily;
        this.addFamilyMember = addFamilyMember;
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

    @PostMapping
    public ResponseEntity<FamilySummary> create(Principal principal, @Valid @RequestBody CreateFamilyRequest request) {
        UUID userId = requireUserId(principal);
        Family family = createFamily.run(userId, request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(family));
    }

    @PostMapping("/{familyId}/members")
    public ResponseEntity<MemberSummary> addMember(Principal principal,
                                                   @PathVariable UUID familyId,
                                                   @Valid @RequestBody AddMemberRequest request) {
        UUID userId = requireUserId(principal);
        FamilyMember member = addFamilyMember.run(userId, familyId, request.userId(), request.role());
        return ResponseEntity.status(HttpStatus.CREATED).body(toMemberSummary(member));
    }

    private UUID requireUserId(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            throw new IllegalArgumentException("Authentication required");
        }
        return userId;
    }

    private FamilySummary toSummary(Family f) {
        return new FamilySummary(f.getId(), f.getName());
    }

    private MemberSummary toMemberSummary(FamilyMember m) {
        return new MemberSummary(m.getId(), m.getFamily().getId(), m.getUser().getId(), m.getRole().name());
    }

    public record FamilySummary(UUID id, String name) {}

    public record CreateFamilyRequest(@NotBlank String name) {}

    public record AddMemberRequest(@NotNull UUID userId, @NotNull FamilyMemberRole role) {}

    public record MemberSummary(UUID id, UUID familyId, UUID userId, String role) {}
}
