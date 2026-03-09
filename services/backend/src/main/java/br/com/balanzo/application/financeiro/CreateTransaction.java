package br.com.balanzo.application.financeiro;

import br.com.balanzo.domain.financeiro.entity.Account;
import br.com.balanzo.domain.financeiro.entity.Transaction;
import br.com.balanzo.domain.financeiro.entity.TransactionType;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.financeiro.AccountRepository;
import br.com.balanzo.infrastructure.persistence.financeiro.TransactionRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTransaction {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public CreateTransaction(TransactionRepository tr, AccountRepository ar, UserRepository ur) {
        this.transactionRepository = tr;
        this.accountRepository = ar;
        this.userRepository = ur;
    }

    @Transactional
    public Transaction run(UUID userId, UUID accountId, BigDecimal amount, String currency,
                           TransactionType type, LocalDate date, String description) {
        User createdBy = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        if (!account.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("Account does not belong to user");
        }

        Transaction tx = new Transaction(account, amount, currency, type, date, createdBy);
        if (description != null && !description.isBlank()) {
            tx.setDescription(description);
        }

        return transactionRepository.save(tx);
    }
}
