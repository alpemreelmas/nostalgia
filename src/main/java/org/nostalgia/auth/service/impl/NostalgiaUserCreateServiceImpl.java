package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.mapper.NostalgiaUserCreateRequestToDomainMapper;
import org.nostalgia.auth.model.request.NostalgiaUserCreateRequest;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.port.NostalgiaUserReadPort;
import org.nostalgia.auth.port.NostalgiaUserSavePort;
import org.nostalgia.auth.service.NostalgiaUserCreateService;
import org.nostalgia.auth.service.NostalgiaUserMailService;
import org.nostalgia.auth.util.exception.NostalgiaRolesNotExistException;
import org.nostalgia.auth.util.exception.NostalgiaUserAlreadyExistsByEmailAddressException;
import org.nostalgia.common.util.NostalgiaRandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service implementation for creating new users in the system.
 * <p>
 * This class is responsible for handling the creation of new users,
 * including validation of input data, ensuring uniqueness of email addresses and phone numbers,
 * and setting roles. It ensures that the new user is properly activated and associated with
 * the correct institution. Additionally, it sends a password creation email to the new user
 * after successful creation.
 * </p>
 */
@Service
@Transactional
@RequiredArgsConstructor
class NostalgiaUserCreateServiceImpl implements NostalgiaUserCreateService {

    private final NostalgiaUserSavePort userSavePort;
    private final NostalgiaUserReadPort userReadPort;
    private final NostalgiaRoleReadPort roleReadPort;
    private final NostalgiaUserMailService userMailService;

    private final NostalgiaIdentity identity;


    private final NostalgiaUserCreateRequestToDomainMapper userCreateRequestToDomainMapper = NostalgiaUserCreateRequestToDomainMapper.initialize();


    /**
     * Creates a new user based on the provided request data.
     * <p>
     * Validates the email address and phone number for uniqueness, checks the existence of roles,
     * and assigns the roles to the user. The user is then activated and associated with the correct
     * institution. After successful creation, a password creation email is sent to the user.
     * </p>
     *
     * @param createRequest The request object containing data for the new user.
     * @throws NostalgiaUserAlreadyExistsByEmailAddressException if the email address is already associated with another user.
     * @throws NostalgiaRolesNotExistException                   if any of the provided role IDs do not exist.
     */
    @Override
    public void create(final NostalgiaUserCreateRequest createRequest) {

        this.validateEmailAddress(createRequest.getEmailAddress());

        final NostalgiaUser user = userCreateRequestToDomainMapper.map(createRequest);

        this.validateRolesAndSet(user, createRequest.getRoleIds());

        user.activate();

        user.setPassword(
                NostalgiaUser.Password.builder()
                        .value(NostalgiaRandomUtil.generateText(15))
                        .build()
        );

        final NostalgiaUser savedUser = userSavePort.save(user);

        userMailService.sendPasswordCreateEmail(savedUser);
    }

    /**
     * Validates the uniqueness of the provided email address.
     * Checks if there is any existing user with the same email address.
     *
     * @param emailAddress The email address to be validated.
     * @throws NostalgiaUserAlreadyExistsByEmailAddressException if the email address is already associated with another user.
     */
    private void validateEmailAddress(String emailAddress) {
        userReadPort.findByEmailAddress(emailAddress)
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
