package br.com.balanzo.infrastructure.persistence.financeiro;

import br.com.balanzo.domain.financeiro.entity.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByAccountIdOrderByDateDesc(UUID accountId, Pageable pageable);

    List<Transaction> findByAccountIdAndDateBetweenOrderByDateDesc(
            UUID accountId, LocalDate start, LocalDate end);
}
