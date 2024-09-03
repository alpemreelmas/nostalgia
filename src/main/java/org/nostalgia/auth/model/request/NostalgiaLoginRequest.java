package org.nostalgia.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaSourcePage;
import org.nostalgia.common.util.validation.EmailAddress;

/**
 * Represents a login request of the Ays application.
 *
 * <p>
 * This class encapsulates the user's email address, password, and the source page from which the login request originated.
 *
 * <p>
 * It provides getter and setter methods for accessing and modifying the email address, password, and source page fields.
 */
@Getter
@Setter
public class NostalgiaLoginRequest {

    @EmailAddress
    @NotBlank
    private String emailAddress;

    @NotBlank
    private String password;

}
