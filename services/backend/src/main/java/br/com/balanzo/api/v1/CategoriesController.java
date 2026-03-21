package br.com.balanzo.api.v1;

import br.com.balanzo.application.classificacao.CreateCategory;
import br.com.balanzo.common.security.CurrentUserResolver;
import br.com.balanzo.domain.classificacao.entity.Category;
import br.com.balanzo.domain.classificacao.entity.CategoryType;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.infrastructure.persistence.classificacao.CategoryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {

    private final CategoryRepository categoryRepository;
    private final CreateCategory createCategory;
    private final CurrentUserResolver currentUser;

    public CategoriesController(CategoryRepository categoryRepository, CreateCategory createCategory,
                                CurrentUserResolver currentUser) {
        this.categoryRepository = categoryRepository;
        this.createCategory = createCategory;
        this.currentUser = currentUser;
    }

    @GetMapping
    public ResponseEntity<List<CategorySummary>> list(Principal principal,
                                                      @RequestParam(required = false) CategoryType type,
                                                      @RequestParam(required = false) UUID familyId) {
        return currentUser.resolve(principal)
                .map(userId -> {
                    List<Category> categories = familyId != null
                            ? categoryRepository.findByOwnerScopeAndOwnerFamilyId(OwnerScope.family, familyId)
                            : categoryRepository.findByOwnerScopeAndOwnerUserId(OwnerScope.user, userId);
                    if (type != null) {
                        categories = categories.stream().filter(c -> c.getType() == type).toList();
                    }
                    return ResponseEntity.ok(categories.stream().map(this::toSummary).toList());
                })
                .orElse(ResponseEntity.ok(List.of()));
    }

    @PostMapping
    public ResponseEntity<CategorySummary> create(Principal principal, @Valid @RequestBody CreateCategoryRequest req) {
        UUID userId = currentUser.require(principal);
        Category cat = createCategory.run(userId, req.name(), req.type(), req.ownerScope(),
                req.familyId(), req.parentId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(cat));
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
