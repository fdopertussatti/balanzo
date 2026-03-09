package br.com.balanzo.infrastructure.persistence.familia;

import br.com.balanzo.domain.familia.entity.Family;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, UUID> {

    List<Family> findByCreatedById(UUID userId);
}
