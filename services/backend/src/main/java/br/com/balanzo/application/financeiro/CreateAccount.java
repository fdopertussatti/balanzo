package br.com.balanzo.application.financeiro;

import br.com.balanzo.domain.financeiro.entity.Account;
import br.com.balanzo.domain.financeiro.entity.AccountType;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.financeiro.AccountRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAccount {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public CreateAccount(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Account run(UUID userId, String name, AccountType type, String currency, String institution) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Account account = new Account(owner, name, type);
        if (currency != null && !currency.isBlank()) {
            account.setCurrency(currency);
        }
        if (institution != null && !institution.isBlank()) {
            account.setInstitution(institution);
        }

        return accountRepository.save(account);
    }
}
