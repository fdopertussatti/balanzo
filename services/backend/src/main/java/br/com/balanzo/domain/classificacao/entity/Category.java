package br.com.balanzo.domain.classificacao.entity;

import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.identidade.entity.User;
import jakarta.persistence.Column;
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
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_scope", nullable = false)
    private OwnerScope ownerScope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_family_id")
    private Family ownerFamily;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Category() {}

    public Category(String name, CategoryType type, OwnerScope ownerScope) {
        this.name = name;
        this.type = type;
        this.ownerScope = ownerScope;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }
    public Category getParent() { return parent; }
    public void setParent(Category parent) { this.parent = parent; }
    public OwnerScope getOwnerScope() { return ownerScope; }
    public User getOwnerUser() { return ownerUser; }
    public void setOwnerUser(User ownerUser) { this.ownerUser = ownerUser; }
    public Family getOwnerFamily() { return ownerFamily; }
    public void setOwnerFamily(Family ownerFamily) { this.ownerFamily = ownerFamily; }
    public Instant getCreatedAt() { return createdAt; }
}
