package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.auth.model.request.NostalgiaRoleCreateRequest;
import org.nostalgia.auth.port.NostalgiaPermissionReadPort;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.port.NostalgiaRoleSavePort;
import org.nostalgia.auth.service.NostalgiaRoleCreateService;
import org.nostalgia.auth.util.exception.NostalgiaPermissionNotExistException;
import org.nostalgia.auth.util.exception.NostalgiaRoleAlreadyExistsByNameException;
import org.nostalgia.auth.util.exception.NostalgiaUserNotSuperAdminException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service implementation for creating roles in the system.
 * <p>
 * This service handles the creation of roles based on the provided create request. It verifies the uniqueness
 * of the role name, checks the existence of permissions, and saves the new role with the associated permissions.
 * </p>
 */
@Service
@Transactional
@RequiredArgsConstructor
class NostalgiaRoleCreateServiceImpl implements NostalgiaRoleCreateService {

    private final NostalgiaRoleReadPort roleReadPort;
    private final NostalgiaRoleSavePort roleSavePort;
    private final NostalgiaPermissionReadPort permissionReadPort;

    private final NostalgiaIdentity identity;

    /**
     * Creates a new role based on the provided create request.
     * <p>
     * This method validates the uniqueness of the role name, checks the existence of permissions,
     * and saves the new role with the associated permissions.
     * </p>
     *
     * @param createRequest the request object containing the role name and permission IDs
     * @throws NostalgiaRoleAlreadyExistsByNameException if a role with the same name already exists
     * @throws NostalgiaPermissionNotExistException      if any of the specified permission IDs do not exist
     * @throws NostalgiaUserNotSuperAdminException       if the current user is not authorized to assign super permissions
     */
    @Override
    public void create(final NostalgiaRoleCreateRequest createRequest) {

        this.checkExistingRoleName(createRequest.getName());

        final List<NostalgiaPermission> permissions = this.checkExistingPermissionsAndGet(createRequest.getPermissionIds());

        final NostalgiaRole role = NostalgiaRole.builder()
                .name(createRequest.getName())
                .permissions(permissions)
                .status(NostalgiaRoleStatus.ACTIVE)
                .build();

        roleSavePort.save(role);
    }

    /**
     * Checks if a role with the specified name already exists.
     *
     * @param name the name of the role to check
     * @throws NostalgiaRoleAlreadyExistsByNameException if a role with the same name already exists
     */
    private void checkExistingRoleName(final String name) {
        roleReadPort.findByName(name).ifPresent(role -> {
            throw new NostalgiaRoleAlreadyExistsByNameException(name);
        });
    }

    /**
     * Checks the existence of permissions based on the provided permission IDs.
     * Verifies if all permission IDs exist and validates super admin restrictions.
     *
     * @param permissionIds the set of permission IDs to check
     * @return the list of permissions corresponding to the provided IDs
     * @throws NostalgiaPermissionNotExistException if any of the permission IDs do not exist
     * @throws NostalgiaUserNotSuperAdminException  if the current user is not authorized to assign super permissions
     */
    private List<NostalgiaPermission> checkExistingPermissionsAndGet(final Set<String> permissionIds) {
        final List<NostalgiaPermission> permissions = permissionReadPort.findAllByIds(permissionIds);

        if (permissions.size() != permissionIds.size()) {

            final List<String> notExistsPermissionIds = permissionIds.stream()
                    .filter(permissionId -> permissions.stream()
                            .noneMatch(permissionEntity -> permissionEntity.getId().equals(permissionId)))
                    .toList();

            throw new NostalgiaPermissionNotExistException(notExistsPermissionIds);
        }

        if (identity.isSuperAdmin()) {
            return permissions;
        }

        boolean haveSuperPermissions = permissions.stream().anyMatch(NostalgiaPermission::isSuper);
        if (haveSuperPermissions) {
            throw new NostalgiaUserNotSuperAdminException(identity.getUserId());
        }

        return permissions;
    }

}
