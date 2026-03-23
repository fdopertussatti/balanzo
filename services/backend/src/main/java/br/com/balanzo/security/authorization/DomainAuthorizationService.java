package br.com.balanzo.security.authorization;

import br.com.balanzo.common.exception.ForbiddenException;
import br.com.balanzo.domain.familia.entity.FamilyMemberRole;
import org.springframework.stereotype.Service;

/**
 * Orchestrates domain authorization decisions.
 * Use cases delegate permission checks here.
 *
 * @see docs/02-arquitetura/estrategia-de-autorizacao-e-compartilhamento-familiar.md
 */
@Service
public class DomainAuthorizationService {

    /**
     * Verifies the user can perform the operation on the resource.
     *
     * @throws ForbiddenException when not authorized
     */
    public void requireAuthorized(AuthorizationContext context, ResourceScope resource) {
        if (!isAuthorized(context, resource)) {
            throw new ForbiddenException("Not authorized to perform this operation");
        }
    }

    /**
     * Checks if the user can perform the operation.
     */
    public boolean isAuthorized(AuthorizationContext context, ResourceScope resource) {
        if (context == null || resource == null) {
            return false;
        }

        boolean isOwner = context.userId().equals(resource.ownerUserId());
        boolean isInFamily = resource.familyId() != null && context.familyIds().contains(resource.familyId());

        return switch (context.operation()) {
            case VIEW -> canView(isOwner, isInFamily, resource.visibility());
            case EDIT -> canEdit(isOwner, isInFamily, resource.visibility());
            case MANAGE -> canManage(isOwner, isInFamily, resource.visibility(), context, resource);
        };
    }

    private boolean canView(boolean isOwner, boolean isInFamily, ResourceScope.VisibilityLevel visibility) {
        if (isOwner) return true;
        return switch (visibility) {
            case PRIVATE, ANALYTICAL_ONLY -> false;
            case SHARED_READ, SHARED_EDIT, SHARED_MANAGE -> isInFamily;
        };
    }

    private boolean canEdit(boolean isOwner, boolean isInFamily, ResourceScope.VisibilityLevel visibility) {
        if (isOwner) return true;
        return switch (visibility) {
            case PRIVATE, ANALYTICAL_ONLY, SHARED_READ -> false;
            case SHARED_EDIT, SHARED_MANAGE -> isInFamily;
        };
    }

    /**
     * Manage: owner always; for family-scoped SHARED_MANAGE, only admin/owner role (per strategy doc).
     */
    private boolean canManage(boolean isOwner, boolean isInFamily, ResourceScope.VisibilityLevel visibility,
                              AuthorizationContext context, ResourceScope resource) {
        if (isOwner) return true;
        if (visibility != ResourceScope.VisibilityLevel.SHARED_MANAGE || !isInFamily) return false;
        var role = resource.familyId() != null ? context.familyRoles().get(resource.familyId()) : null;
        return role == FamilyMemberRole.owner || role == FamilyMemberRole.admin;
    }
}
