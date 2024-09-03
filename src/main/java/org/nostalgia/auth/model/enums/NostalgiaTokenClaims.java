package org.nostalgia.auth.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * AysTokenClaims enum represents the possible claims that can be included in a JWT token for an AYS system.
 * The enum constants represent the name of the claim and the associated value is stored as a final String field.
 */
@Getter
@RequiredArgsConstructor
public enum NostalgiaTokenClaims {

    TYPE("typ"),
    USER_ID("userId"),
    USERNAME("username"),
    USER_TYPE("userType"),
    USER_EMAIL_ADDRESS("userEmailAddress"),
    USER_PERMISSIONS("userPermissions"),
    USER_FULL_NAME("userFullName"),
    USER_LAST_LOGIN_AT("userLastLoginAt"),
    ISSUED_AT("iat"),
    EXPIRES_AT("exp"),
    ALGORITHM("alg");

    private final String value;

}
