package br.com.balanzo.application.financeiro;

import br.com.balanzo.common.exception.ForbiddenException;
import br.com.balanzo.common.exception.ResourceNotFoundException;
import br.com.balanzo.domain.financeiro.entity.Transaction;
import br.com.balanzo.domain.financeiro.entity.VisibilityScope;
import br.com.balanzo.infrastructure.persistence.classificacao.CategoryRepository;
import br.com.balanzo.infrastructure.persistence.financeiro.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTransaction {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public UpdateTransaction(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Partial update. Only non-null fields are applied.
     * Caller must be account owner.
     */
    @Transactional
    public Transaction run(UUID userId, UUID accountId, UUID transactionId, BigDecimal amount,
                           LocalDate date, String description, UUID categoryId, VisibilityScope visibilityScope) {
        var tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", transactionId));
        if (!tx.getAccount().getId().equals(accountId)) {
            throw new ResourceNotFoundException("Transaction", transactionId);
        }
        if (!tx.getAccount().getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Not authorized to update this transaction");
        }

        if (amount != null) tx.setAmount(amount);
        if (date != null) tx.setDate(date);
        if (description != null) tx.setDescription(description);
        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(tx::setCategory);
        }
        if (visibilityScope != null) tx.setVisibilityScope(visibilityScope);

        return transactionRepository.save(tx);
    }
}
