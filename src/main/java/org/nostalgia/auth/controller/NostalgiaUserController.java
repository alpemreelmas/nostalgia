package org.nostalgia.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.mapper.NostalgiaUserToResponseMapper;
import org.nostalgia.auth.model.mapper.NostalgiaUserToUsersResponseMapper;
import org.nostalgia.auth.model.request.NostalgiaUserCreateRequest;
import org.nostalgia.auth.model.request.NostalgiaUserUpdateRequest;
import org.nostalgia.auth.model.response.NostalgiaUserResponse;
import org.nostalgia.auth.model.response.NostalgiaUsersResponse;
import org.nostalgia.auth.service.NostalgiaUserCreateService;
import org.nostalgia.auth.service.NostalgiaUserUpdateService;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.response.NostalgiaPageResponse;
import org.nostalgia.common.model.response.NostalgiaResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.nostalgia.auth.service.NostalgiaUserReadService;
import org.nostalgia.auth.model.request.NostalgiaUserListRequest;

/**
 * REST controller for managing users.
 * This controller provides endpoints for listing users.
 *
 * @see NostalgiaUserReadService
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class NostalgiaUserController {

    private final NostalgiaUserReadService userReadService;
    private final NostalgiaUserCreateService userCreateService;
    private final NostalgiaUserUpdateService userUpdateService;


    private final NostalgiaUserToUsersResponseMapper userToUsersResponseMapper = NostalgiaUserToUsersResponseMapper.initialize();
    private final NostalgiaUserToResponseMapper userToResponseMapper = NostalgiaUserToResponseMapper.initialize();


    /**
     * POST /users : Retrieve all users based on the provided filtering and pagination criteria.
     * <p>
     * This endpoint handles the retrieval of users based on the filtering and pagination criteria
     * provided in {@link NostalgiaUserListRequest}. The user must have the 'user:list' authority to access this endpoint.
     * </p>
     *
     * @param request the request object containing filtering and pagination criteria.
     * @return an {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public NostalgiaResponse<NostalgiaPageResponse<NostalgiaUsersResponse>> findAll(@RequestBody @Valid NostalgiaUserListRequest request) {

        NostalgiaPage<NostalgiaUser> pageOfUsers = userReadService.findAll(request);

        final NostalgiaPageResponse<NostalgiaUsersResponse> pageOfUsersResponse = NostalgiaPageResponse.<NostalgiaUsersResponse>builder()
                .of(pageOfUsers)
                .content(userToUsersResponseMapper.map(pageOfUsers.getContent()))
                .build();

        return NostalgiaResponse.successOf(pageOfUsersResponse);
    }


    /**
     * GET /user/{id} : Retrieve the details of a user by its ID.
     * <p>
     * This endpoint handles the retrieval of a user by its ID. The user must have the 'user:detail'
     * authority to access this endpoint.
     * </p>
     *
     * @param id The ID of the user to retrieve.
     * @return An {@link NostalgiaResponse} containing the {@link NostalgiaUserResponse} if the user is found.
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('user:detail')")
    public NostalgiaResponse<NostalgiaUserResponse> findById(@PathVariable @UUID final String id) {
        final NostalgiaUser user = userReadService.findById(id);
        final NostalgiaUserResponse userResponse = userToResponseMapper.map(user);
        return NostalgiaResponse.successOf(userResponse);
    }


    /**
     * Endpoint for creating a new user.
     * <p>
     * This endpoint is secured and only accessible to users with the 'user:create' authority.
     * It handles the creation of a new user by delegating the request to the user creation service.
     * The request body is validated to ensure all required fields are present and valid.
     * </p>
     *
     * @param createRequest The request object containing data for the new user.
     * @return NostalgiaResponse with a success message and no data.
     */
    @PostMapping("/user")
    @PreAuthorize("hasAnyAuthority('user:create')")
    public NostalgiaResponse<Void> create(@RequestBody @Valid final NostalgiaUserCreateRequest createRequest) {
        userCreateService.create(createRequest);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * Update an existing user based on the provided request data.
     * <p>
     * This method is mapped to handle HTTP PUT requests to "/user/{id}". It requires
     * the user to have the 'user:update' authority to access.
     * </p>
     *
     * @param id            The ID of the user to update.
     * @param updateRequest The request object containing updated data for the user.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public NostalgiaResponse<Void> update(@PathVariable @UUID final String id,
                                    @RequestBody @Valid final NostalgiaUserUpdateRequest updateRequest) {

        userUpdateService.update(id, updateRequest);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * PATCH /user/{id}/activate : Activates a user account with the given ID.
     * <p>
     * This endpoint is protected and requires the caller to have the authority
     * 'user:update'. The user ID must be a valid UUID.
     * </p>
     *
     * @param id The UUID of the user to be activated.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PatchMapping("/user/{id}/activate")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public NostalgiaResponse<Void> activate(@PathVariable @UUID final String id) {
        userUpdateService.activate(id);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * PATCH /user/{id}/passivate : Passivates (deactivates) a user account with the given ID.
     * <p>
     * This endpoint is protected and requires the caller to have the authority
     * 'user:update'. The user ID must be a valid UUID.
     * </p>
     *
     * @param id The UUID of the user to be passivated.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PatchMapping("/user/{id}/passivate")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public NostalgiaResponse<Void> passivate(@PathVariable @UUID final String id) {
        userUpdateService.passivate(id);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * DELETE /user/{id} : Deletes a user account with the given ID.
     * <p>
     * This endpoint is protected and requires the caller to have the authority
     * 'user:delete'. The user ID must be a valid UUID.
     * </p>
     *
     * @param id The UUID of the user to be deleted.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public NostalgiaResponse<Void> delete(@PathVariable @UUID final String id) {
        userUpdateService.delete(id);
        return NostalgiaResponse.SUCCESS;
    }

}
