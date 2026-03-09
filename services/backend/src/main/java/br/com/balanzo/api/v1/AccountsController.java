package br.com.balanzo.api.v1;

import br.com.balanzo.application.financeiro.CreateAccount;
import br.com.balanzo.domain.financeiro.entity.Account;
import br.com.balanzo.domain.financeiro.entity.AccountType;
import br.com.balanzo.infrastructure.persistence.financeiro.AccountRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountRepository accountRepository;
    private final CreateAccount createAccount;

    public AccountsController(AccountRepository accountRepository, CreateAccount createAccount) {
        this.accountRepository = accountRepository;
        this.createAccount = createAccount;
    }

    @GetMapping
    public ResponseEntity<List<AccountSummary>> list(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.ok(List.of());
        }

        var accounts = accountRepository.findByOwnerId(userId);
        var summaries = accounts.stream()
                .map(this::toSummary)
                .toList();
        return ResponseEntity.ok(summaries);
    }

    @PostMapping
    public ResponseEntity<AccountSummary> create(Principal principal, @Valid @RequestBody CreateAccountRequest request) {
        UUID userId = requireUserId(principal);
        Account account = createAccount.run(
                userId,
                request.name(),
                request.type(),
                request.currency(),
                request.institution()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(account));
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

    private AccountSummary toSummary(Account a) {
        return new AccountSummary(
                a.getId(),
                a.getName(),
                a.getType().name(),
                a.getCurrency(),
                a.getInstitution()
        );
    }

    public record AccountSummary(UUID id, String name, String type, String currency, String institution) {}

    public record CreateAccountRequest(
            @NotBlank String name,
            @NotNull AccountType type,
            String currency,
            String institution
    ) {}
}
