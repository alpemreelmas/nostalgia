package org.nostalgia.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.nostalgia.auth.model.NostalgiaToken;
import org.nostalgia.auth.model.mapper.NostalgiaTokenToResponseMapper;
import org.nostalgia.auth.model.request.*;
import org.nostalgia.auth.model.response.NostalgiaTokenResponse;
import org.nostalgia.auth.service.NostalgiaAuthService;
import org.nostalgia.auth.service.NostalgiaUserPasswordService;
import org.nostalgia.common.model.response.NostalgiaResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth controller to perform authentication api operations.
 */
@Validated
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
class NostalgiaAuthController {

    private final NostalgiaAuthService authService;
    private final NostalgiaUserPasswordService userPasswordService;


    private final NostalgiaTokenToResponseMapper tokenToTokenResponseMapper = NostalgiaTokenToResponseMapper.initialize();


    /**
     * This endpoint allows user to login to platform.
     *
     * @param loginRequest An NostalgiaLoginRequest object required to log in to platform.
     * @return An NostalgiaResponse containing an NostalgiaTokenResponse object and the HTTP status code (200 OK).
     */
    @PostMapping("/token")
    public NostalgiaResponse<NostalgiaTokenResponse> authenticate(@RequestBody @Valid NostalgiaLoginRequest loginRequest) {
        final NostalgiaToken token = authService.authenticate(loginRequest);
        final NostalgiaTokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return NostalgiaResponse.successOf(tokenResponse);
    }


    /**
     * This endpoint allows user to refresh token.
     *
     * @param refreshRequest An AysTokenRefreshRequest object used to send a token.
     * @return An AysResponse containing an AysTokenResponse object and the HTTP status code (200 OK).
     */
    @PostMapping("/token/refresh")
    public NostalgiaResponse<NostalgiaTokenResponse> refreshToken(@RequestBody @Valid NostalgiaTokenRefreshRequest refreshRequest) {
        final NostalgiaToken token = authService.refreshAccessToken(refreshRequest.getRefreshToken());
        final NostalgiaTokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return NostalgiaResponse.successOf(tokenResponse);
    }


    /**
     * Endpoint for invalidating a token. Only users with the 'USER' authority are allowed to access this endpoint.
     * It invalidates the access token and refresh token associated with the provided refresh token.
     *
     * @param invalidateRequest the request object containing the refresh token to invalidate
     * @return AysResponse with a success message and no data
     */
    @PostMapping("/token/invalidate")
    public NostalgiaResponse<Void> invalidateTokens(@RequestBody @Valid NostalgiaTokenInvalidateRequest invalidateRequest) {
        authService.invalidateTokens(invalidateRequest.getRefreshToken());
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * This endpoint allows a user to request a password create.
     *
     * @param forgotPasswordRequest An AysForgotPasswordRequest object containing the user's email address.
     * @return An AysResponse indicating the success of the password create request.
     */
    @PostMapping("/password/forgot")
    public NostalgiaResponse<Void> forgotPassword(@RequestBody @Valid NostalgiaPasswordForgotRequest forgotPasswordRequest) {
        userPasswordService.forgotPassword(forgotPasswordRequest);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * Endpoint for checking the existence and validity of a password reset token.
     * <p>
     * This endpoint handles the verification of a password reset token by its ID.
     * It delegates the check to the user password service.
     * </p>
     *
     * @param id The ID of the password reset token to be checked.
     * @return AysResponse with a success message and no data.
     */
    @GetMapping("/password/{id}/validity")
    public NostalgiaResponse<Void> checkPasswordChangingValidity(@PathVariable @UUID String id) {
        userPasswordService.checkPasswordChangingValidity(id);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * Handles the request to create a new password for a user.
     * <p>
     * This endpoint processes a request to set a new password for a user identified
     * by the provided password ID. It validates the request and updates the user's
     * password if the request meets the required criteria.
     *
     * @param id            The unique identifier of the user for whom the password is being created.
     * @param createRequest The request body containing the new password details.
     * @return A response indicating the success of the operation.
     */
    @PostMapping("/password/{id}")
    public NostalgiaResponse<Void> forgotPassword(@PathVariable @UUID String id,
                                            @RequestBody @Valid NostalgiaPasswordCreateRequest createRequest) {

        userPasswordService.createPassword(id, createRequest);
        return NostalgiaResponse.SUCCESS;
    }

}
