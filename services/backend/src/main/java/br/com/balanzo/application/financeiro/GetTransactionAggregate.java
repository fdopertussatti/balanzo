package br.com.balanzo.application.financeiro;

import br.com.balanzo.domain.financeiro.entity.Account;
import br.com.balanzo.domain.financeiro.entity.Transaction;
import br.com.balanzo.domain.financeiro.entity.VisibilityScope;
import br.com.balanzo.infrastructure.persistence.financeiro.AccountRepository;
import br.com.balanzo.infrastructure.persistence.financeiro.TransactionRepository;
import br.com.balanzo.security.authorization.AuthorizationContext;
import br.com.balanzo.security.authorization.AuthorizationContextResolver;
import br.com.balanzo.security.authorization.DomainAuthorizationService;
import br.com.balanzo.security.authorization.ResourceScope;
import br.com.balanzo.security.authorization.TransactionResourceScopeFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Returns aggregated totals for account transactions.
 * For family members: includes ANALYTICAL_ONLY in aggregates (consolidated view per strategy doc).
 */
@Service
public class GetTransactionAggregate {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AuthorizationContextResolver authContextResolver;
    private final DomainAuthorizationService authorizationService;

    public GetTransactionAggregate(TransactionRepository transactionRepository,
                                   AccountRepository accountRepository,
                                   AuthorizationContextResolver authContextResolver,
                                   DomainAuthorizationService authorizationService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.authContextResolver = authContextResolver;
        this.authorizationService = authorizationService;
    }

    public AggregateResult run(UUID userId, UUID accountId, LocalDate start, LocalDate end) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new br.com.balanzo.common.exception.ResourceNotFoundException("Account", accountId));

        boolean isOwner = account.getOwner().getId().equals(userId);
        UUID accountFamilyId = account.getFamily() != null ? account.getFamily().getId() : null;

        if (!isOwner) {
            if (accountFamilyId == null) {
                throw new br.com.balanzo.common.exception.ResourceNotFoundException("Account", accountId);
            }
            var membershipCtx = authContextResolver.resolve(userId, AuthorizationContext.Operation.VIEW);
            var familyAccess = new ResourceScope(null, accountFamilyId, ResourceScope.VisibilityLevel.SHARED_READ);
            if (!authorizationService.isAuthorized(membershipCtx, familyAccess)) {
                throw new br.com.balanzo.common.exception.ResourceNotFoundException("Account", accountId);
            }
        }

        List<Transaction> transactions;
        if (start != null && end != null) {
            transactions = transactionRepository.findByAccountIdAndDateBetweenOrderByDateDesc(
                    accountId, start, end);
        } else {
            transactions = transactionRepository.findByAccountIdOrderByDateDesc(
                    accountId, org.springframework.data.domain.PageRequest.of(0, 1000));
        }

        var viewCtx = authContextResolver.resolve(userId, AuthorizationContext.Operation.VIEW);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalTransfer = BigDecimal.ZERO;
        int count = 0;

        for (Transaction tx : transactions) {
            boolean includeInAggregate = isOwner || includeInAggregateForFamilyMember(viewCtx, account, tx);
            if (!includeInAggregate) continue;

            count++;
            BigDecimal amt = tx.getAmount();
            switch (tx.getType()) {
                case income -> totalIncome = totalIncome.add(amt);
                case expense -> totalExpense = totalExpense.add(amt);
                case transfer -> totalTransfer = totalTransfer.add(amt);
            }
        }

        return new AggregateResult(totalIncome, totalExpense, totalTransfer, count);
    }

    /**
     * Family member: include if visible in detail OR ANALYTICAL_ONLY (consolidation per strategy).
     */
    private boolean includeInAggregateForFamilyMember(AuthorizationContext ctx, Account account, Transaction tx) {
        boolean canViewDetail = authorizationService.isAuthorized(
                ctx, TransactionResourceScopeFactory.forTransaction(account, tx));
        if (canViewDetail) return true;
        return tx.getVisibilityScope() == VisibilityScope.analytical_only;
    }

    public record AggregateResult(BigDecimal totalIncome, BigDecimal totalExpense,
                                  BigDecimal totalTransfer, int transactionCount) {}
}
