package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.auth.model.request.NostalgiaUserUpdateRequest;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.port.NostalgiaUserReadPort;
import org.nostalgia.auth.port.NostalgiaUserSavePort;
import org.nostalgia.auth.service.NostalgiaUserUpdateService;
import org.nostalgia.auth.util.exception.*;
import org.nostalgia.common.model.NostalgiaPhoneNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service implementation for updating users.
 * This service handles the update operation of existing users based on the provided update request.
 */
@Service
@Transactional
@RequiredArgsConstructor
class NostalgiaUserUpdateServiceImpl implements NostalgiaUserUpdateService {

    private final NostalgiaUserReadPort userReadPort;
    private final NostalgiaUserSavePort userSavePort;
    private final NostalgiaRoleReadPort roleReadPort;

    private final NostalgiaIdentity identity;


    /**
     * Updates an existing user with the given ID based on the provided update request.
     *
     * @param id            The unique identifier of the user to be updated.
     * @param updateRequest The request object containing the updated user information.
     * @throws NostalgiaUserNotExistByIdException         if a user with the given ID does not exist.
     * @throws NostalgiaUserIsNotActiveOrPassiveException if the user's status is not valid for an update.
     */
    @Override
    public void update(final String id,
                       final NostalgiaUserUpdateRequest updateRequest) {

        final NostalgiaUser user = userReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaUserNotExistByIdException(id));

        if (!(user.isActive() || user.isPassive())) {
            throw new NostalgiaUserIsNotActiveOrPassiveException(id);
        }

        this.validateEmailAddress(user, updateRequest.getEmailAddress());
        this.validateRolesAndSet(user, updateRequest.getRoleIds());

        user.setFullName(updateRequest.getFullName());
        user.setEmailAddress(updateRequest.getEmailAddress());

        userSavePort.save(user);
    }


    /**
     * Activates a user by ID if the user is currently passive.
     * This method retrieves the user by the provided ID and activates the user
     *
     * @param id The unique identifier of the user to be activated.
     * @throws NostalgiaUserNotExistByIdException if a user with the given ID does not exist.
     * @throws NostalgiaUserNotPassiveException   if the user is not in a passive state and cannot be activated.
     */
    @Override
    public void activate(String id) {

        final NostalgiaUser user = userReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaUserNotExistByIdException(id));

        if (!user.isPassive()) {
            throw new NostalgiaUserNotPassiveException(NostalgiaUserStatus.PASSIVE);
        }

        user.activate();
        userSavePort.save(user);
    }

    /**
     * Passivates (deactivates) a user by ID if the user is currently active.
     * This method retrieves the user by the provided ID and deactivates the user.
     *
     * @param id The unique identifier of the user to be passivated.
     * @throws NostalgiaUserNotExistByIdException if a user with the given ID does not exist.
     * @throws NostalgiaUserNotActiveException    if the user is not in an active state and cannot be passivated.
     */
    @Override
    public void passivate(String id) {

        final NostalgiaUser user = userReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaUserNotExistByIdException(id));

        if (!user.isActive()) {
            throw new NostalgiaUserNotActiveException(id);
        }

        user.passivate();
        userSavePort.save(user);
    }


    /**
     * Deletes a user account by its ID.
     * <p>
     * This method retrieves a user by the given ID and ensures the user's institution ID
     * matches the identity's institution ID. If the user is already marked as deleted,
     * an {@link NostalgiaUserAlreadyDeletedException} is thrown. Otherwise, the user's status
     * is set to deleted and the changes are saved.
     * </p>
     *
     * @param id The ID of the user to delete.
     * @throws NostalgiaUserNotExistByIdException   If no user with the given ID exists or if the user does not belong to the caller's institution.
     * @throws NostalgiaUserAlreadyDeletedException If the user is already marked as deleted.
     */
    @Override
    public void delete(final String id) {

        final NostalgiaUser user = userReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaUserNotExistByIdException(id));

        if (user.isDeleted()) {
            throw new NostalgiaUserAlreadyDeletedException(id);
        }

        user.delete();
        userSavePort.save(user);
    }


    /**
     * Validates the uniqueness of the provided email address.
     * Checks if there is any existing user with the same email address.
     *
     * @param user         The user being updated.
     * @param emailAddress The email address to be validated.
     * @throws NostalgiaUserAlreadyExistsByEmailAddressException if the email address is already associated with another user.
     */
    private void validateEmailAddress(NostalgiaUser user, String emailAddress) {

        if (user.getEmailAddress().equals(emailAddress)) {
            return;
        }

        userReadPort.findByEmailAddress(emailAddress)
                .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                .ifPresent(existingUser -> {
                    throw new NostalgiaUserAlreadyExistsByEmailAddressException(emailAddress);
                });
    }


    /**
     * Checks the existence of roles by their IDs and returns the corresponding role entities.
     *
     * @param user    The user being updated.
     * @param roleIds The set of role IDs to be checked and retrieved.
     * @throws NostalgiaRolesNotExistException if any of the provided role IDs do not exist.
     */
    private void validateRolesAndSet(final NostalgiaUser user, final Set<String> roleIds) {

        boolean isRoleNotChanged = user.getRoles().stream()
                .allMatch(role -> roleIds.contains(role.getId()));
        if (isRoleNotChanged) {
            return;
        }

        final List<NostalgiaRole> roles = roleReadPort.findAllByIds(roleIds).stream()
                .filter(NostalgiaRole::isActive)
                .toList();

        if (roles.size() == roleIds.size()) {
            user.setRoles(roles);
            return;
        }

        final List<String> notExistsRoleIds = roleIds.stream()
                .filter(roleId -> roles.stream()
                        .noneMatch(roleEntity -> roleEntity.getId().equals(roleId)))
                .toList();

        throw new NostalgiaRolesNotExistException(notExistsRoleIds);
    }

}
