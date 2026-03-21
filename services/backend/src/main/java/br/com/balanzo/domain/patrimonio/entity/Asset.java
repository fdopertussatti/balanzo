package br.com.balanzo.domain.patrimonio.entity;

import br.com.balanzo.domain.classificacao.entity.OwnerScope;
import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_scope", nullable = false)
    private OwnerScope ownerScope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_family_id")
    private Family ownerFamily;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(name = "estimated_value", precision = 19, scale = 4)
    private BigDecimal estimatedValue;

    @Column(length = 3)
    private String currency;

    @Column(name = "valuation_date")
    private LocalDate valuationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected Asset() {}

    public static Asset create(String name, String type, OwnerScope ownerScope) {
        Asset a = new Asset();
        a.setName(name);
        a.setType(type);
        a.setOwnerScope(ownerScope);
        return a;
    }

    public UUID getId() { return id; }
    public OwnerScope getOwnerScope() { return ownerScope; }
    public void setOwnerScope(OwnerScope ownerScope) { this.ownerScope = ownerScope; }
    public User getOwnerUser() { return ownerUser; }
    public void setOwnerUser(User ownerUser) { this.ownerUser = ownerUser; }
    public Family getOwnerFamily() { return ownerFamily; }
    public void setOwnerFamily(Family ownerFamily) { this.ownerFamily = ownerFamily; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getEstimatedValue() { return estimatedValue; }
    public void setEstimatedValue(BigDecimal estimatedValue) { this.estimatedValue = estimatedValue; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public LocalDate getValuationDate() { return valuationDate; }
    public void setValuationDate(LocalDate valuationDate) { this.valuationDate = valuationDate; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
