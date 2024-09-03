package org.nostalgia.auth.controller;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.model.mapper.NostalgiaPermissionToPermissionsResponseMapper;
import org.nostalgia.auth.model.response.NostalgiaPermissionsResponse;
import org.nostalgia.auth.service.NostalgiaPermissionService;
import org.nostalgia.common.model.response.NostalgiaResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing permissions.
 * This controller provides endpoints for retrieving permissions.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class NostalgiaPermissionController {

    private final NostalgiaPermissionService permissionService;


    private final NostalgiaPermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = NostalgiaPermissionToPermissionsResponseMapper.initialize();


    /**
     * Retrieves all permissions.
     * <p>
     * This endpoint returns a list of all permissions. The user must have the 'role:create'
     * or 'role:update' authority to access this endpoint.
     * </p>
     *
     * @return {@link NostalgiaResponse} containing a list of {@link NostalgiaPermissionsResponse}.
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAnyAuthority('role:detail','role:create', 'role:update')")
    public NostalgiaResponse<List<NostalgiaPermissionsResponse>> findAll() {

        final List<NostalgiaPermission> permissions = permissionService.findAll();

        final List<NostalgiaPermissionsResponse> permissionsResponses = permissionToPermissionsResponseMapper
                .map(permissions);
        return NostalgiaResponse.successOf(permissionsResponses);
    }

}
