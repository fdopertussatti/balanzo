package br.com.balanzo.infrastructure.persistence.patrimonio;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.patrimonio.entity.Asset;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, UUID> {

    List<Asset> findByOwnerScopeAndOwnerUserId(OwnerScope scope, UUID userId);

    List<Asset> findByOwnerScopeAndOwnerFamilyId(OwnerScope scope, UUID familyId);
}
