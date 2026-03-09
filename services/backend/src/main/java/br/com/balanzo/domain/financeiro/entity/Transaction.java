package br.com.balanzo.domain.financeiro.entity;

import br.com.balanzo.domain.classificacao.entity.Category;
import br.com.balanzo.domain.identidade.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Convert(converter = VisibilityScopeConverter.class)
    @Column(name = "visibility_scope", nullable = false)
    private VisibilityScope visibilityScope = VisibilityScope.private_;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Transaction() {}

    public Transaction(Account account, BigDecimal amount, String currency, TransactionType type,
                       LocalDate date, User createdBy) {
        this.account = account;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.date = date;
        this.createdBy = createdBy;
    }

    public UUID getId() { return id; }
    public Account getAccount() { return account; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public VisibilityScope getVisibilityScope() { return visibilityScope; }
    public void setVisibilityScope(VisibilityScope visibilityScope) { this.visibilityScope = visibilityScope; }
    public User getCreatedBy() { return createdBy; }
    public Instant getCreatedAt() { return createdAt; }
}
