package br.com.balanzo.security.authorization;

import br.com.balanzo.domain.financeiro.entity.Account;
import br.com.balanzo.domain.financeiro.entity.Transaction;
import java.util.UUID;

/**
 * Builds {@link ResourceScope} for transaction authorization.
 * Owner is the account owner; family comes from the account when linked to a family.
 * Per-transaction visibility applies to non-owners (shared visibility per strategy doc).
 */
public final class TransactionResourceScopeFactory {

    private TransactionResourceScopeFactory() {}

    public static ResourceScope forTransaction(Account account, Transaction transaction) {
        UUID ownerUserId = account.getOwner().getId();
        UUID familyId = account.getFamily() != null ? account.getFamily().getId() : null;
        var level = VisibilityScopeMapper.toResourceLevel(transaction.getVisibilityScope());
        return new ResourceScope(ownerUserId, familyId, level);
    }
}
