package com.portafolio.compliancehub.auth.domain.model.aggregates;

import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;
import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditableAbstractAggregateRoot<User> {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    private User(String email, String password, String firstName, String lastName, RoleType role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.active = true;
    }

    public static User create(String email, String hashedPassword, String firstName, String lastName, RoleType role) {
        return new User(email, hashedPassword, firstName, lastName, role);
    }

    public void assignRole(RoleType newRole) {
        this.role = newRole;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

}
