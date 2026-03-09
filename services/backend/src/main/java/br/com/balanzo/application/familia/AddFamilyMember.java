package br.com.balanzo.application.familia;

import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.familia.entity.FamilyMember;
import br.com.balanzo.domain.familia.entity.FamilyMemberRole;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddFamilyMember {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;

    public AddFamilyMember(FamilyRepository familyRepository,
                           FamilyMemberRepository familyMemberRepository,
                           UserRepository userRepository) {
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FamilyMember run(UUID requesterUserId, UUID familyId, UUID memberUserId, FamilyMemberRole role) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new IllegalArgumentException("Family not found: " + familyId));

        FamilyMember requester = familyMemberRepository.findByFamilyIdAndUserId(familyId, requesterUserId)
                .orElseThrow(() -> new IllegalArgumentException("Requester is not a member of this family"));
        if (requester.getRole() != FamilyMemberRole.owner && requester.getRole() != FamilyMemberRole.admin) {
            throw new IllegalArgumentException("Only owner or admin can add members");
        }

        User member = userRepository.findById(memberUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + memberUserId));

        familyMemberRepository.findByFamilyIdAndUserId(familyId, memberUserId)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("User already member of this family");
                });

        FamilyMember newMember = new FamilyMember(family, member, role);
        return familyMemberRepository.save(newMember);
    }
}
