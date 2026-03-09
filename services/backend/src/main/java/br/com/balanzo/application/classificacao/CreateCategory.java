package br.com.balanzo.application.classificacao;

import br.com.balanzo.domain.classificacao.entity.Category;
import br.com.balanzo.domain.classificacao.entity.CategoryType;
import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.classificacao.CategoryRepository;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCategory {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public CreateCategory(CategoryRepository categoryRepository, UserRepository userRepository,
                          FamilyRepository familyRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Transactional
    public Category run(UUID userId, String name, CategoryType type, OwnerScope ownerScope,
                        UUID familyId, UUID parentId) {
        Category cat = new Category(name, type, ownerScope);

        if (ownerScope == OwnerScope.user) {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            cat.setOwnerUser(owner);
        } else if (ownerScope == OwnerScope.family && familyId != null) {
            Family family = familyRepository.findById(familyId)
                    .orElseThrow(() -> new IllegalArgumentException("Family not found: " + familyId));
            cat.setOwnerFamily(family);
        } else {
            throw new IllegalArgumentException("Family required for family scope");
        }

        if (parentId != null) {
            categoryRepository.findById(parentId).ifPresent(cat::setParent);
        }

        return categoryRepository.save(cat);
    }
}
