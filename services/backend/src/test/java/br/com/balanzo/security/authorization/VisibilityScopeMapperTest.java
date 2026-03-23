package br.com.balanzo.security.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.balanzo.domain.financeiro.entity.VisibilityScope;
import org.junit.jupiter.api.Test;

class VisibilityScopeMapperTest {

    @Test
    void mapsAllVisibilityScopes() {
        assertEquals(ResourceScope.VisibilityLevel.PRIVATE, VisibilityScopeMapper.toResourceLevel(VisibilityScope.private_));
        assertEquals(ResourceScope.VisibilityLevel.SHARED_READ, VisibilityScopeMapper.toResourceLevel(VisibilityScope.shared_read));
        assertEquals(ResourceScope.VisibilityLevel.SHARED_EDIT, VisibilityScopeMapper.toResourceLevel(VisibilityScope.shared_edit));
        assertEquals(ResourceScope.VisibilityLevel.SHARED_MANAGE, VisibilityScopeMapper.toResourceLevel(VisibilityScope.shared_manage));
        assertEquals(ResourceScope.VisibilityLevel.ANALYTICAL_ONLY, VisibilityScopeMapper.toResourceLevel(VisibilityScope.analytical_only));
    }

    @Test
    void nullDefaultsToPrivate() {
        assertEquals(ResourceScope.VisibilityLevel.PRIVATE, VisibilityScopeMapper.toResourceLevel(null));
    }
}
