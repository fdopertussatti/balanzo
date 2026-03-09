package br.com.balanzo.api.v1;

import br.com.balanzo.domain.classificacao.entity.Category;
import br.com.balanzo.domain.classificacao.entity.CategoryType;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.infrastructure.persistence.classificacao.CategoryRepository;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final CategoryRepository categoryRepository;

    public CategoriesController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategorySummary>> list(Principal principal,
                                                      @RequestParam(required = false) CategoryType type,
                                                      @RequestParam(required = false) UUID familyId) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.ok(List.of());
        }

        List<Category> categories;
        if (familyId != null) {
            categories = categoryRepository.findByOwnerScopeAndOwnerFamilyId(OwnerScope.family, familyId);
        } else {
            categories = categoryRepository.findByOwnerScopeAndOwnerUserId(OwnerScope.user, userId);
        }
        categories = categories.stream().filter(c -> c.getType() == type).toList();

        var summaries = categories.stream().map(this::toSummary).toList();
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

    private CategorySummary toSummary(Category c) {
        return new CategorySummary(
                c.getId(),
                c.getName(),
                c.getType().name(),
                c.getParent() != null ? c.getParent().getId() : null
        );
    }

    public record CategorySummary(UUID id, String name, String type, UUID parentId) {}

    public record CreateCategoryRequest(
            @NotBlank String name,
            @NotNull CategoryType type,
            @NotNull OwnerScope ownerScope,
            UUID familyId,
            UUID parentId
    ) {}
}
