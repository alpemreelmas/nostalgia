package org.nostalgia.auth.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.nostalgia.common.model.request.NostalgiaPhoneNumberRequest;
import org.nostalgia.common.util.validation.EmailAddress;
import org.nostalgia.common.util.validation.Name;

import java.util.Set;

/**
 * Request object for updating a user's details.
 * <p>
 * This class encapsulates the data required to update an existing user's details.
 * Each field is validated to ensure it meets the necessary requirements.
 */
@Getter
@Setter
public class NostalgiaUserUpdateRequest {

    @Name
    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;

    @Name
    @NotBlank
    @Size(min = 2, max = 100)
    private String lastName;

    @EmailAddress
    @NotBlank
    @Size(min = 2, max = 255)
    private String emailAddress;

    @Valid
    @NotNull
    private NostalgiaPhoneNumberRequest phoneNumber;

    @Name
    @NotBlank
    @Size(min = 2, max = 100)
    private String city;

    @NotEmpty
    private Set<@NotBlank @UUID String> roleIds;

}
