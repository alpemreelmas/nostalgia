package org.nostalgia.auth.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.nostalgia.auth.model.enums.NostalgiaTokenClaims;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.common.model.BaseDomainModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain model representing a user entity for data transfer between the service layer and controller.
 * This class extends {@link BaseDomainModel} to inherit common properties and behavior.
 * It encapsulates user-specific information such as identification, contact details, status,
 * authentication credentials, roles, and associated institution.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NostalgiaUser extends BaseDomainModel {

    private String id;
    private String emailAddress;
    private String fullName;
    private NostalgiaUserStatus status;

    private Password password;
    private LoginAttempt loginAttempt;

    private List<NostalgiaRole> roles;

    /**
     * Checks if the user's status is active.
     *
     * @return {@code true} if the user's status is {@link NostalgiaUserStatus#ACTIVE}, otherwise {@code false}.
     */
    public boolean isActive() {
        return NostalgiaUserStatus.ACTIVE.equals(this.status);
    }

    /**
     * Checks if the user's status is passive.
     *
     * @return {@code true} if the user's status is {@link NostalgiaUserStatus#PASSIVE}, otherwise {@code false}.
     */
    public boolean isPassive() {
        return NostalgiaUserStatus.PASSIVE.equals(this.status);
    }

    /**
     * Checks if the user's status is deleted.
     *
     * @return {@code true} if the user's status is {@link NostalgiaUserStatus#DELETED}, otherwise {@code false}.
     */
    public boolean isDeleted() {
        return NostalgiaUserStatus.DELETED.equals(this.status);
    }

    /**
     * Sets the user's status to {@link NostalgiaUserStatus#ACTIVE}, marking the user as active.
     */
    public void activate() {
        this.status = NostalgiaUserStatus.ACTIVE;
    }

    /**
     * Sets the user's status to {@link NostalgiaUserStatus#PASSIVE}, marking the user as passive.
     */
    public void passivate() {
        this.status = NostalgiaUserStatus.PASSIVE;
    }

    /**
     * Sets the user's status to {@link NostalgiaUserStatus#DELETED}, marking the user as deleted.
     */
    public void delete() {
        this.status = NostalgiaUserStatus.DELETED;
    }

    /**
     * Sets the user's status to {@link NostalgiaUserStatus#NOT_VERIFIED}, indicating the user's verification status.
     * This method typically sets the status to indicate the user's account has not yet been verified.
     */
    public void notVerify() {
        this.status = NostalgiaUserStatus.NOT_VERIFIED;
    }


    /**
     * Domain model representing user password information.
     * This class encapsulates the unique identifier and encrypted value of a user's password.
     */
    @Getter
    @Setter
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    public static class Password extends BaseDomainModel {

        private String id;
        private String value;
        private LocalDateTime forgotAt;

    }


    /**
     * Domain model representing user login attempt information.
     * This class encapsulates the unique identifier and timestamp of a user's last login attempt.
     * It provides a method to update the last login timestamp to the current time.
     */
    @Getter
    @Setter
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    public static class LoginAttempt extends BaseDomainModel {

        private String id;
        private LocalDateTime lastLoginAt;

        public void success() {
            this.lastLoginAt = LocalDateTime.now();
        }

    }

    /**
     * Generates JWT claims based on the user's information for authentication and authorization.
     * The claims include user-specific data such as institution ID, institution name, user ID, first name,
     * last name, email address, and permissions associated with the user.
     * If available, it also includes the timestamp of the user's last successful login attempt.
     *
     * @return JWT claims containing user-related information.
     */
    public Claims getClaims() {
        final ClaimsBuilder claimsBuilder = Jwts.claims();
        claimsBuilder.add(NostalgiaTokenClaims.USER_ID.getValue(), this.id);
        claimsBuilder.add(NostalgiaTokenClaims.USER_FULL_NAME.getValue(), this.fullName);
        claimsBuilder.add(NostalgiaTokenClaims.USER_EMAIL_ADDRESS.getValue(), this.emailAddress);
        claimsBuilder.add(NostalgiaTokenClaims.USER_PERMISSIONS.getValue(), this.getPermissionNames());


        if (this.loginAttempt != null && this.loginAttempt.lastLoginAt != null) {
            claimsBuilder.add(NostalgiaTokenClaims.USER_LAST_LOGIN_AT.getValue(), this.loginAttempt.lastLoginAt.toString());
        }

        return claimsBuilder.build();
    }

    private List<String> getPermissionNames() {
        return this.roles.stream()
                .map(NostalgiaRole::getPermissions)
                .flatMap(List::stream)
                .map(NostalgiaPermission::getName)
                .toList();
    }

}
