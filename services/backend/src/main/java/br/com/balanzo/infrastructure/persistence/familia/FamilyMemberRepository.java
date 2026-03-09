package br.com.balanzo.infrastructure.persistence.familia;

import br.com.balanzo.domain.familia.entity.FamilyMember;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {

    List<FamilyMember> findByUserIdAndStatus(UUID userId, FamilyMemberStatus status);

    Optional<FamilyMember> findByFamilyIdAndUserId(UUID familyId, UUID userId);
}
