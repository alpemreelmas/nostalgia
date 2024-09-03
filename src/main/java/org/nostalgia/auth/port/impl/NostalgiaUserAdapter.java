package org.nostalgia.auth.port.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.NostalgiaUserFilter;
import org.nostalgia.auth.model.entity.NostalgiaUserEntity;
import org.nostalgia.auth.model.mapper.NostalgiaUserEntityToDomainMapper;
import org.nostalgia.auth.model.mapper.NostalgiaUserToEntityMapper;
import org.nostalgia.auth.port.NostalgiaUserReadPort;
import org.nostalgia.auth.port.NostalgiaUserSavePort;
import org.nostalgia.auth.repository.NostalgiaUserRepository;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;
import org.nostalgia.common.model.NostalgiaPhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adapter class implementing both {@link NostalgiaUserReadPort} and {@link NostalgiaUserSavePort} interfaces.
 * Retrieves {@link NostalgiaUser} entities from the repository, saves {@link NostalgiaUser} entities to the database,
 * and maps between domain models and entity models.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaUserAdapter implements NostalgiaUserReadPort, NostalgiaUserSavePort {

    private final NostalgiaUserRepository userRepository;


    private final NostalgiaUserToEntityMapper userToEntityMapper = NostalgiaUserToEntityMapper.initialize();
    private final NostalgiaUserEntityToDomainMapper userEntityToDomainMapper = NostalgiaUserEntityToDomainMapper.initialize();


    /**
     * Finds all users with pagination and optional filtering.
     * <p>
     * This method uses the provided {@link NostalgiaPageable} for pagination and {@link NostalgiaUserFilter} for filtering.
     * It returns a paginated list of {@link NostalgiaUser} domain models.
     * </p>
     *
     * @param NostalgiaPageable the pagination configuration
     * @param filter      the filter for users
     * @return a paginated list of users
     */
    @Override
    public NostalgiaPage<NostalgiaUser> findAll(NostalgiaPageable NostalgiaPageable, NostalgiaUserFilter filter) {

        final Pageable pageable = NostalgiaPageable.toPageable();

        final Specification<NostalgiaUserEntity> specification = filter.toSpecification();

        final Page<NostalgiaUserEntity> userEntitysPage = userRepository.findAll(specification, pageable);

        final List<NostalgiaUser> users = userEntityToDomainMapper.map(userEntitysPage.getContent());

        return NostalgiaPage.of(filter, userEntitysPage, users);
    }


    /**
     * Retrieves an {@link NostalgiaUser} by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An optional containing the {@link NostalgiaUser} if found, otherwise empty.
     */
    @Override
    public Optional<NostalgiaUser> findById(final String id) {
        Optional<NostalgiaUserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userEntityToDomainMapper::map);
    }


    /**
     * Retrieves an {@link NostalgiaUser} by its email address.
     *
     * @param emailAddress The email address of the user to retrieve.
     * @return An optional containing the {@link NostalgiaUser} if found, otherwise empty.
     */
    @Override
    public Optional<NostalgiaUser> findByEmailAddress(final String emailAddress) {
        Optional<NostalgiaUserEntity> userEntity = userRepository.findByEmailAddress(emailAddress);
        return userEntity.map(userEntityToDomainMapper::map);
    }


    /**
     * Checks if a user with the given email address exists in the repository.
     *
     * @param emailAddress The email address to check for existence.
     * @return true if a user with the given email address exists, otherwise false.
     */
    @Override
    public boolean existsByEmailAddress(final String emailAddress) {
        return userRepository.existsByEmailAddress(emailAddress);
    }


    /**
     * Finds a user by their phone number, which is a concatenation of country code and line number.
     *
     * @param phoneNumber the concatenated phone number (country code + line number) of the user to be found
     * @return an optional containing the {@link NostalgiaUser} with the given phone number, or an empty optional if not found
     */
    @Override
    public Optional<NostalgiaUser> findByPhoneNumber(NostalgiaPhoneNumber phoneNumber) {
        Optional<NostalgiaUserEntity> userEntity = userRepository.findByCountryCodeAndLineNumber(
                phoneNumber.getCountryCode(),
                phoneNumber.getLineNumber()
        );
        return userEntity.map(userEntityToDomainMapper::map);
    }


    /**
     * Finds a user by their password ID.
     *
     * @param passwordId the ID of the password to search for.
     * @return an Optional containing the found user, or empty if no user was found with the given password ID.
     */
    @Override
    public Optional<NostalgiaUser> findByPasswordId(final String passwordId) {
        Optional<NostalgiaUserEntity> userEntity = userRepository.findByPasswordId(passwordId);
        return userEntity.map(userEntityToDomainMapper::map);
    }


    /**
     * Checks if a user with the given phone number exists in the repository.
     *
     * @param phoneNumber The phone number to check for existence.
     * @return true if a user with the given phone number exists, otherwise false.
     */
    @Override
    public boolean existsByPhoneNumber(final NostalgiaPhoneNumber phoneNumber) {
        return userRepository.existsByCountryCodeAndLineNumber(
                phoneNumber.getCountryCode(),
                phoneNumber.getLineNumber()
        );
    }


    /**
     * Saves an {@link NostalgiaUser} to the database.
     *
     * @param user The {@link NostalgiaUser} to save.
     * @return The saved {@link NostalgiaUser} after persistence.
     */
    @Override
    @Transactional
    public NostalgiaUser save(final NostalgiaUser user) {

        final NostalgiaUserEntity userEntity = userToEntityMapper.map(user);

        if (user.getPassword() != null) {
            userEntity.getPassword().setUser(userEntity);
        }

        if (user.getLoginAttempt() != null) {
            userEntity.getLoginAttempt().setUser(userEntity);
        }

        final NostalgiaUserEntity savedUserEntity = userRepository.save(userEntity);
        return userEntityToDomainMapper.map(savedUserEntity);
    }

}
