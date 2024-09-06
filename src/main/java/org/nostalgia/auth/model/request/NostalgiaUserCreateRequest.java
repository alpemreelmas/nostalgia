package org.nostalgia.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.nostalgia.common.util.validation.EmailAddress;
import org.nostalgia.common.util.validation.Name;

import java.util.Set;

/**
 * Represents a request for creating a new user in the system.
 * This class contains all the necessary details required to create a user, including their personal information,
 * contact details, and roles. The fields are validated to ensure they meet the required constraints.
 * <p>
 * This class uses various annotations for validation to ensure the data integrity and consistency.
 * </p>
 */
@Getter
@Setter
public class NostalgiaUserCreateRequest {

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

    @NotEmpty
    private Set<@NotBlank @UUID String> roleIds;

}
