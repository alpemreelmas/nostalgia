package org.nostalgia.auth.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.common.model.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a user entity in the application, extending from {@link BaseEntity}.
 * This entity class maps to the database table "AYS_USER".
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "NOSTALGIA_USER")
public class NostalgiaUserEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private NostalgiaUserStatus status;

    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private PasswordEntity password;

    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private LoginAttemptEntity loginAttempt;

    @ManyToMany
    @JoinTable(
            name = "NOSTALGIA_USER_ROLE_RELATION",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private List<NostalgiaRoleEntity> roles;


    /**
     * Nested entity representing the password information of the user.
     * This entity is mapped to the database table "AYS_USER_PASSWORD".
     */
    @Entity
    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @Table(name = "NOSTALGIA_USER_PASSWORD")
    public static class PasswordEntity extends BaseEntity {

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "VALUE")
        private String value;

        @OneToOne
        @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
        private NostalgiaUserEntity user;

        @Column(name = "FORGOT_AT")
        private LocalDateTime forgotAt;

    }


    /**
     * Nested entity representing the login attempt information of the user.
     * This entity is mapped to the database table "AYS_USER_LOGIN_ATTEMPT".
     */
    @Entity
    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @Table(name = "NOSTALGIA_USER_LOGIN_ATTEMPT")
    public static class LoginAttemptEntity extends BaseEntity {

        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "LAST_LOGIN_AT")
        private LocalDateTime lastLoginAt;

        @OneToOne
        @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
        private NostalgiaUserEntity user;

    }

}
