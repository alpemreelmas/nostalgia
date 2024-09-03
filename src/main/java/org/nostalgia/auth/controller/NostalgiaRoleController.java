package org.nostalgia.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.mapper.NostalgiaRoleToResponseMapper;
import org.nostalgia.auth.model.mapper.NostalgiaRoleToRolesResponseMapper;
import org.nostalgia.auth.model.mapper.NostalgiaRoleToRolesSummaryResponseMapper;
import org.nostalgia.auth.model.request.NostalgiaRoleCreateRequest;
import org.nostalgia.auth.model.request.NostalgiaRoleListRequest;
import org.nostalgia.auth.model.request.NostalgiaRoleUpdateRequest;
import org.nostalgia.auth.model.response.NostalgiaRoleResponse;
import org.nostalgia.auth.model.response.NostalgiaRolesResponse;
import org.nostalgia.auth.model.response.NostalgiaRolesSummaryResponse;
import org.nostalgia.auth.service.NostalgiaRoleCreateService;
import org.nostalgia.auth.service.NostalgiaRoleReadService;
import org.nostalgia.auth.service.NostalgiaRoleUpdateService;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.response.NostalgiaPageResponse;
import org.nostalgia.common.model.response.NostalgiaResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing roles.
 * This controller provides endpoints for listing, creating, updating, and retrieving roles.
 *
 * @see NostalgiaRoleReadService
 * @see NostalgiaRoleCreateService
 * @see NostalgiaRoleUpdateService
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class NostalgiaRoleController {

    private final NostalgiaRoleReadService roleReadService;
    private final NostalgiaRoleCreateService roleCreateService;
    private final NostalgiaRoleUpdateService roleUpdateService;


    private final NostalgiaRoleToRolesResponseMapper roleToRolesResponseMapper = NostalgiaRoleToRolesResponseMapper.initialize();
    private final NostalgiaRoleToRolesSummaryResponseMapper roleToRolesSummaryResponseMapper = NostalgiaRoleToRolesSummaryResponseMapper.initialize();
    private final NostalgiaRoleToResponseMapper roleToResponseMapper = NostalgiaRoleToResponseMapper.initialize();


    /**
     * GET /roles/summary : Retrieve a summary of all roles.
     * <p>
     * This endpoint handles the retrieval of a summary of all roles.
     * The user must have the 'user:create' or 'user:update' authority to access this endpoint.
     * </p>
     *
     * @return an {@link NostalgiaResponse} containing a list of {@link NostalgiaRolesSummaryResponse} objects,
     * which represent the summary of roles.
     */
    @GetMapping("/roles/summary")
    @PreAuthorize("hasAnyAuthority('user:create', 'user:update')")
    public NostalgiaResponse<List<NostalgiaRolesSummaryResponse>> findAllSummary() {

        final List<NostalgiaRole> roles = roleReadService.findAll();

        final List<NostalgiaRolesSummaryResponse> permissionsResponses = roleToRolesSummaryResponseMapper
                .map(roles);
        return NostalgiaResponse.successOf(permissionsResponses);
    }


    /**
     * POST /roles : Retrieve all roles based on the provided filtering and pagination criteria.
     * <p>
     * This endpoint handles the retrieval of roles based on the filtering and pagination criteria
     * provided in {@link NostalgiaRoleListRequest}. The user must have the 'role:list' authority to access this endpoint.
     * </p>
     *
     * @param listRequest the request object containing filtering and pagination criteria.
     * @return an {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PostMapping("/roles")
    @PreAuthorize("hasAnyAuthority('role:list')")
    public NostalgiaResponse<NostalgiaPageResponse<NostalgiaRolesResponse>> findAll(
            @RequestBody @Valid final NostalgiaRoleListRequest listRequest) {

        final NostalgiaPage<NostalgiaRole> pageOfRoles = roleReadService.findAll(listRequest);

        final NostalgiaPageResponse<NostalgiaRolesResponse> pageOfRolesResponse = NostalgiaPageResponse.<NostalgiaRolesResponse>builder()
                .of(pageOfRoles)
                .content(roleToRolesResponseMapper.map(pageOfRoles.getContent()))
                .build();
        return NostalgiaResponse.successOf(pageOfRolesResponse);
    }


    /**
     * GET /role/{id} : Retrieve the details of a role by its ID.
     * <p>
     * This endpoint handles the retrieval of a role by its ID. The user must have the 'role:detail'
     * authority to access this endpoint.
     * </p>
     *
     * @param id The ID of the role to retrieve.
     * @return An {@link NostalgiaResponse} containing the {@link NostalgiaRoleResponse} if the role is found.
     */
    @GetMapping("/role/{id}")
    @PreAuthorize("hasAuthority('role:detail')")
    public NostalgiaResponse<NostalgiaRoleResponse> findById(@PathVariable @UUID String id) {
        final NostalgiaRole role = roleReadService.findById(id);
        final NostalgiaRoleResponse roleResponse = roleToResponseMapper.map(role);
        return NostalgiaResponse.successOf(roleResponse);
    }


    /**
     * POST /role : Create a new role.
     * <p>
     * This endpoint handles the creation of a new role based on the provided {@link NostalgiaRoleCreateRequest}.
     * The user must have the 'role:create' authority to access this endpoint.
     * </p>
     *
     * @param createRequest the request object containing the details for the new role.
     * @return an {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PostMapping("/role")
    @PreAuthorize("hasAnyAuthority('role:create')")
    public NostalgiaResponse<Void> create(@RequestBody @Valid NostalgiaRoleCreateRequest createRequest) {
        roleCreateService.create(createRequest);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * Update an existing role based on the provided request data.
     * <p>
     * This method is mapped to handle HTTP PUT requests to "/role/{id}". It requires
     * the user to have the 'role:update' authority to access.
     * </p>
     *
     * @param id            The ID of the role to update.
     * @param updateRequest The request object containing updated data for the role.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PutMapping("/role/{id}")
    @PreAuthorize("hasAnyAuthority('role:update')")
    public NostalgiaResponse<Void> update(@PathVariable @UUID final String id,
                                    @RequestBody @Valid final NostalgiaRoleUpdateRequest updateRequest) {

        roleUpdateService.update(id, updateRequest);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * PATCH /role/{id}/activate : Activate an existing role.
     * <p>
     * This endpoint handles the activation of a role based on its ID. The user must have the
     * 'role:update' authority to access this endpoint.
     * </p>
     *
     * @param id The ID of the role to activate.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PatchMapping("/role/{id}/activate")
    @PreAuthorize("hasAnyAuthority('role:update')")
    public NostalgiaResponse<Void> activate(@PathVariable @UUID final String id) {
        roleUpdateService.activate(id);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * PATCH /role/{id}/passivate : Passivate an existing role.
     * <p>
     * This endpoint handles the passivation of a role based on its ID. The user must have the
     * 'role:update' authority to access this endpoint.
     * </p>
     *
     * @param id The ID of the role to passivate.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @PatchMapping("/role/{id}/passivate")
    @PreAuthorize("hasAnyAuthority('role:update')")
    public NostalgiaResponse<Void> passivate(@PathVariable @UUID final String id) {
        roleUpdateService.passivate(id);
        return NostalgiaResponse.SUCCESS;
    }


    /**
     * Delete an existing role by its ID.
     * <p>
     * This method is mapped to handle HTTP DELETE requests to "/role/{id}". It requires
     * the user to have the 'role:delete' authority to access.
     * </p>
     *
     * @param id The ID of the role to delete.
     * @return An {@link NostalgiaResponse} indicating the success of the operation.
     */
    @DeleteMapping("/role/{id}")
    @PreAuthorize("hasAnyAuthority('role:delete')")
    public NostalgiaResponse<Void> delete(@PathVariable @UUID final String id) {
        roleUpdateService.delete(id);
        return NostalgiaResponse.SUCCESS;
    }

}
