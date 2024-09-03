package org.nostalgia.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AysTokenInvalidateRequest is a request class used for invalidating a token.
 * It includes a refresh token field.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NostalgiaTokenInvalidateRequest {

    @NotBlank
    private String refreshToken;

}
