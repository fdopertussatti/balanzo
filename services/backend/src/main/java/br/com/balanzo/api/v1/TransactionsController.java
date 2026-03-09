package br.com.balanzo.api.v1;

import br.com.balanzo.application.financeiro.CreateTransaction;
import br.com.balanzo.domain.financeiro.entity.Transaction;
import br.com.balanzo.domain.financeiro.entity.TransactionType;
import br.com.balanzo.infrastructure.persistence.financeiro.AccountRepository;
import br.com.balanzo.infrastructure.persistence.financeiro.TransactionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class TransactionsController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CreateTransaction createTransaction;

    public TransactionsController(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository,
                                  CreateTransaction createTransaction) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.createTransaction = createTransaction;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionSummary>> list(Principal principal,
                                                          @PathVariable UUID accountId,
                                                          @RequestParam(required = false) LocalDate start,
                                                          @RequestParam(required = false) LocalDate end) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.ok(List.of());
        }

        var account = accountRepository.findById(accountId).orElse(null);
        if (account == null || !account.getOwner().getId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }

        List<Transaction> transactions;
        if (start != null && end != null) {
            transactions = transactionRepository.findByAccountIdAndDateBetweenOrderByDateDesc(
                    accountId, start, end);
        } else {
            transactions = transactionRepository.findByAccountIdOrderByDateDesc(
                    accountId, org.springframework.data.domain.PageRequest.of(0, 100));
        }

        var summaries = transactions.stream().map(this::toSummary).toList();
        return ResponseEntity.ok(summaries);
    }

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionSummary> create(Principal principal,
                                                     @PathVariable UUID accountId,
                                                     @Valid @RequestBody CreateTransactionRequest request) {
        UUID userId = requireUserId(principal);
        Transaction tx = createTransaction.run(
                userId,
                accountId,
                request.amount(),
                request.currency() != null ? request.currency() : "BRL",
                request.type(),
                request.date(),
                request.description(),
                request.categoryId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(tx));
    }

    private UUID userIdFrom(Principal principal) {
        if (principal instanceof Jwt jwt) {
            var sub = jwt.getSubject();
            if (sub != null) {
                try {
                    return UUID.fromString(sub);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return null;
    }

    private UUID requireUserId(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            throw new IllegalArgumentException("Authentication required");
        }
        return userId;
    }

    private TransactionSummary toSummary(Transaction t) {
        return new TransactionSummary(
                t.getId(),
                t.getAccount().getId(),
                t.getAmount(),
                t.getCurrency(),
                t.getType().name(),
                t.getDate(),
                t.getDescription(),
                t.getCategory() != null ? t.getCategory().getId() : null
        );
    }

    public record TransactionSummary(UUID id, UUID accountId, BigDecimal amount, String currency,
                                     String type, LocalDate date, String description, UUID categoryId) {}

    public record CreateTransactionRequest(
            @NotNull BigDecimal amount,
            String currency,
            @NotNull TransactionType type,
            @NotNull LocalDate date,
            String description,
            UUID categoryId
    ) {}
}
