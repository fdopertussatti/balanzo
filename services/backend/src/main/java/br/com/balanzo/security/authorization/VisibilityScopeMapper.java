package br.com.balanzo.security.authorization;

import br.com.balanzo.domain.financeiro.entity.VisibilityScope;

/**
 * Maps domain {@link VisibilityScope} to authorization {@link ResourceScope.VisibilityLevel}.
 * Names align with DB enum visibility_scope (see modelo conceitual).
 */
public final class VisibilityScopeMapper {

    private VisibilityScopeMapper() {}

    public static ResourceScope.VisibilityLevel toResourceLevel(VisibilityScope scope) {
        if (scope == null) {
            return ResourceScope.VisibilityLevel.PRIVATE;
        }
        return switch (scope) {
            case private_ -> ResourceScope.VisibilityLevel.PRIVATE;
            case shared_read -> ResourceScope.VisibilityLevel.SHARED_READ;
            case shared_edit -> ResourceScope.VisibilityLevel.SHARED_EDIT;
            case shared_manage -> ResourceScope.VisibilityLevel.SHARED_MANAGE;
            case analytical_only -> ResourceScope.VisibilityLevel.ANALYTICAL_ONLY;
        };
    }
}
