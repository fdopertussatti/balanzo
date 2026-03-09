package br.com.balanzo.domain.familia.entity;

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
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "family_member", uniqueConstraints = @UniqueConstraint(columnNames = {"family_id", "user_id"}))
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FamilyMemberRole role = FamilyMemberRole.member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FamilyMemberStatus status = FamilyMemberStatus.active;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt = Instant.now();

    protected FamilyMember() {}

    public FamilyMember(Family family, User user, FamilyMemberRole role) {
        this.family = family;
        this.user = user;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public Family getFamily() {
        return family;
    }

    public User getUser() {
        return user;
    }

    public FamilyMemberRole getRole() {
        return role;
    }

    public void setRole(FamilyMemberRole role) {
        this.role = role;
    }

    public FamilyMemberStatus getStatus() {
        return status;
    }

    public void setStatus(FamilyMemberStatus status) {
        this.status = status;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }
}
