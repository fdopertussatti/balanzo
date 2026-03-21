package br.com.balanzo.api.v1;

import br.com.balanzo.application.patrimonio.CreateAsset;
import br.com.balanzo.common.security.CurrentUserResolver;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.patrimonio.entity.Asset;
import br.com.balanzo.infrastructure.persistence.patrimonio.AssetRepository;
import br.com.balanzo.security.authorization.AuthorizationContext;
import br.com.balanzo.security.authorization.AuthorizationContextResolver;
import br.com.balanzo.security.authorization.DomainAuthorizationService;
import br.com.balanzo.common.exception.DomainException;
import br.com.balanzo.security.authorization.ResourceScope;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetsController {

    private final AssetRepository assetRepository;
    private final CreateAsset createAsset;
    private final CurrentUserResolver currentUser;
    private final AuthorizationContextResolver authContextResolver;
    private final DomainAuthorizationService authService;

    public AssetsController(AssetRepository assetRepository, CreateAsset createAsset,
                            CurrentUserResolver currentUser, AuthorizationContextResolver authContextResolver,
                            DomainAuthorizationService authService) {
        this.assetRepository = assetRepository;
        this.createAsset = createAsset;
        this.currentUser = currentUser;
        this.authContextResolver = authContextResolver;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<AssetSummary>> list(Principal principal,
                                                   @RequestParam(required = false) UUID familyId) {
        UUID userId = currentUser.require(principal);
        if (familyId != null) {
            var ctx = authContextResolver.resolve(userId, AuthorizationContext.Operation.VIEW);
            var scope = new ResourceScope(null, familyId, ResourceScope.VisibilityLevel.SHARED_READ);
            authService.requireAuthorized(ctx, scope);
        }
        List<Asset> assets = familyId != null
                ? assetRepository.findByOwnerScopeAndOwnerFamilyId(OwnerScope.family, familyId)
                : assetRepository.findByOwnerScopeAndOwnerUserId(OwnerScope.user, userId);
        return ResponseEntity.ok(assets.stream().map(this::toSummary).toList());
    }

    @PostMapping
    public ResponseEntity<AssetSummary> create(Principal principal, @Valid @RequestBody CreateAssetRequest request) {
        UUID userId = currentUser.require(principal);
        if (request.ownerScope() == OwnerScope.family && request.familyId() == null) {
            throw new DomainException("familyId is required when ownerScope is family");
        }
        if (request.ownerScope() == OwnerScope.family && request.familyId() != null) {
            var ctx = authContextResolver.resolve(userId, AuthorizationContext.Operation.EDIT);
            var scope = new ResourceScope(null, request.familyId(), ResourceScope.VisibilityLevel.SHARED_EDIT);
            authService.requireAuthorized(ctx, scope);
        }
        Asset asset = createAsset.run(userId, request.name(), request.type(), request.estimatedValue(),
                request.currency(), request.valuationDate(), request.ownerScope(), request.familyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(asset));
    }

    private AssetSummary toSummary(Asset a) {
        return new AssetSummary(a.getId(), a.getName(), a.getType(), a.getEstimatedValue(),
                a.getCurrency(), a.getValuationDate());
    }

    public record AssetSummary(UUID id, String name, String type, BigDecimal estimatedValue,
                               String currency, LocalDate valuationDate) {}

    public record CreateAssetRequest(
            @NotBlank String name,
            @NotBlank String type,
            BigDecimal estimatedValue,
            String currency,
            LocalDate valuationDate,
            @NotNull OwnerScope ownerScope,
            UUID familyId
    ) {}
}
