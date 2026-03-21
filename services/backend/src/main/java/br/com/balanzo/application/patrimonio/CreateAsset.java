package br.com.balanzo.application.patrimonio;

import br.com.balanzo.common.exception.ResourceNotFoundException;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.domain.patrimonio.entity.Asset;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import br.com.balanzo.infrastructure.persistence.patrimonio.AssetRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAsset {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public CreateAsset(AssetRepository assetRepository, UserRepository userRepository,
                       FamilyRepository familyRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Transactional
    public Asset run(UUID userId, String name, String type, BigDecimal estimatedValue,
                     String currency, LocalDate valuationDate, OwnerScope ownerScope, UUID familyId) {
        Asset asset = Asset.create(name, type, ownerScope);
        asset.setEstimatedValue(estimatedValue);
        asset.setCurrency(currency != null ? currency : "BRL");
        asset.setValuationDate(valuationDate);

        if (ownerScope == OwnerScope.user) {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", userId));
            asset.setOwnerUser(owner);
        } else {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Family", familyId));
            asset.setOwnerFamily(family);
        }

        return assetRepository.save(asset);
    }
}
