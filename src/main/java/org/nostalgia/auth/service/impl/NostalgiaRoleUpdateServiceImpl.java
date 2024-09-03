package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.auth.model.request.NostalgiaRoleUpdateRequest;
import org.nostalgia.auth.port.NostalgiaPermissionReadPort;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.port.NostalgiaRoleSavePort;
import org.nostalgia.auth.service.NostalgiaRoleUpdateService;
import org.nostalgia.auth.util.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service implementation for updating roles.
 * This service handles the updating of existing roles based on the provided update request,
 * ensuring permissions and role name uniqueness are validated before saving.
 */
@Service
@Transactional
@RequiredArgsConstructor
class NostalgiaRoleUpdateServiceImpl implements NostalgiaRoleUpdateService {

    private final NostalgiaRoleReadPort roleReadPort;
    private final NostalgiaRoleSavePort roleSavePort;
    private final NostalgiaPermissionReadPort permissionReadPort;

    private final NostalgiaIdentity identity;


    /**
     * Updates an existing role identified by its ID.
     * <p>
     * This method performs checks to ensure the role name is unique and validates the existence of provided permissions.
     * It also verifies that the role belongs to the same institution as the current user's institution.
     * </p>
     *
     * @param id            The ID of the role to update.
     * @param updateRequest The request object containing updated data for the role.
     * @throws NostalgiaRoleAlreadyExistsByNameException if a role with the same name already exists, excluding the current role ID.
     * @throws NostalgiaPermissionNotExistException      if any of the permission IDs provided do not exist.
     * @throws NostalgiaUserNotSuperAdminException       if the current user does not have super admin privileges required for assigning super permissions.
     * @throws NostalgiaRoleNotExistByIdException        if the role with the given ID does not exist or does not belong to the current user's institution.
     */
    @Override
    public void update(final String id,
                       final NostalgiaRoleUpdateRequest updateRequest) {

        final NostalgiaRole role = roleReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaRoleNotExistByIdException(id));

        this.checkExistingRoleNameByWithoutId(id, updateRequest.getName());

        final List<NostalgiaPermission> permissions = this.checkExistingPermissionsAndGet(updateRequest.getPermissionIds());

        role.setName(updateRequest.getName());
        role.setPermissions(permissions);

        roleSavePort.save(role);
    }


    /**
     * Activates an existing role.
     * <p>
     * This method sets the status of the role identified by its ID to active. If the role does not exist,
     * an exception is thrown. Additionally, if the role's status is not {@link NostalgiaRoleStatus#PASSIVE},
     * an exception is thrown.
     * </p>
     *
     * @param id The ID of the role to activate.
     * @throws NostalgiaRoleNotExistByIdException  if a role with the given ID does not exist.
     * @throws NostalgiaInvalidRoleStatusException if the role's current status is not {@link NostalgiaRoleStatus#PASSIVE}.
     */
    @Override
    public void activate(String id) {
        final NostalgiaRole role = roleReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaRoleNotExistByIdException(id));

        if (!role.isPassive()) {
            throw new NostalgiaInvalidRoleStatusException(NostalgiaRoleStatus.PASSIVE);
        }

        role.activate();
        roleSavePort.save(role);
    }


    /**
     * Passivates an existing role.
     * <p>
     * This method sets the status of the role identified by its ID to passivate.
     * It also verifies that the role belongs to the same institution as the current user's institution
     * and no user is assigned to the role.
     * </p>
     *
     * @param id The ID of the role to passivate.
     * @throws NostalgiaRoleNotExistByIdException  if a role with the given ID does not exist.
     * @throws NostalgiaRoleAssignedToUserException if any user is assigned to the role.
     * @throws NostalgiaInvalidRoleStatusException if the role's current status is not {@link NostalgiaRoleStatus#ACTIVE}.
     */
    @Override
    public void passivate(String id) {
        final NostalgiaRole role = roleReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaRoleNotExistByIdException(id));

        if (roleReadPort.isRoleUsing(id)) {
            throw new NostalgiaRoleAssignedToUserException(id);
        }

        if (!role.isActive()) {
            throw new NostalgiaInvalidRoleStatusException(NostalgiaRoleStatus.ACTIVE);
        }

        role.passivate();
        roleSavePort.save(role);
    }


    /**
     * Deletes an existing role identified by its ID.
     * It also verifies that the role belongs to the same institution as the current user's institution.
     *
     * @param id The ID of the role to delete.
     * @throws NostalgiaRoleNotExistByIdException   if no role exists with the provided ID.
     * @throws NostalgiaRoleAssignedToUserException if users are assigned to the role.
     * @throws NostalgiaRoleAlreadyDeletedException if the role is already marked as deleted.
     */
    @Override
    public void delete(final String id) {

        final NostalgiaRole role = roleReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaRoleNotExistByIdException(id));

        if (roleReadPort.isRoleUsing(id)) {
            throw new NostalgiaRoleAssignedToUserException(id);
        }

        if (role.isDeleted()) {
            throw new NostalgiaRoleAlreadyDeletedException(id);
        }

        role.delete();
        roleSavePort.save(role);
    }


    /**
     * Checks the existence of another role with the same name, excluding the current role ID.
     *
     * @param id   The ID of the role being updated.
     * @param name The name to check for uniqueness.
     * @throws NostalgiaRoleAlreadyExistsByNameException if a role with the same name already exists, excluding the current role ID.
     */
    private void checkExistingRoleNameByWithoutId(final String id, final String name) {
        roleReadPort.findByName(name)
                .filter(role -> !id.equals(role.getId()))
                .ifPresent(role -> {
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
