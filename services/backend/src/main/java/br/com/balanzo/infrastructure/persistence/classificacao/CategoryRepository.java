package br.com.balanzo.infrastructure.persistence.classificacao;

import br.com.balanzo.domain.classificacao.entity.Category;
import br.com.balanzo.domain.classificacao.entity.CategoryType;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByOwnerScopeAndOwnerUserId(OwnerScope scope, UUID userId);

    List<Category> findByOwnerScopeAndOwnerFamilyId(OwnerScope scope, UUID familyId);

    List<Category> findByTypeAndParentIdIsNull(CategoryType type);
}
