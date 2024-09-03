package org.nostalgia.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.common.util.validation.EmailAddress;

@Getter
@Setter
public class NostalgiaPasswordForgotRequest {

    @EmailAddress
    @NotBlank
    @Size(min = 2, max = 255)
    private String emailAddress;

}
