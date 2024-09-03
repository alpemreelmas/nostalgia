package org.nostalgia.auth.repository;

import org.nostalgia.auth.model.entity.NostalgiaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on UserEntity objects.
 */
public interface NostalgiaUserRepository extends JpaRepository<NostalgiaUserEntity, String>, JpaSpecificationExecutor<NostalgiaUserEntity> {

    /**
     * Finds a user by emailAddress.
     *
     * @param emailAddress the username of the user to be found
     * @return an optional containing the UserEntity with the given username, or an empty optional if not found
     */
    Optional<NostalgiaUserEntity> findByEmailAddress(String emailAddress);

    /**
     * Finds a user by their phone number.
     *
     * @param countryCode the country code of the user to check
     * @param lineNumber  the phone number of the user to check
     * @return an optional containing the UserEntity with the given phone number, or an empty optional if not found
     */
    Optional<NostalgiaUserEntity> findByCountryCodeAndLineNumber(String countryCode, String lineNumber);

    /**
     * Finds a user entity by the password ID.
     *
     * @param passwordId the ID of the password to search for.
     * @return an Optional containing the found user entity, or empty if no user entity was found with the given password ID.
     */
    Optional<NostalgiaUserEntity> findByPasswordId(String passwordId);

    /**
     * Checks if an {@link NostalgiaUserEntity} exists with the given email.
     *
     * @param emailAddress the email address of the user to check
     * @return true if an {@link NostalgiaUserEntity} exists with the given emailAddress, false otherwise
     */
    boolean existsByEmailAddress(String emailAddress);

    /**
     * Checks if an {@link NostalgiaUserEntity} exists with the given country code and phone number.
     *
     * @param countryCode the country code of the user to check
     * @param lineNumber  the phone number of the user to check
     * @return true if an {@link NostalgiaUserEntity} exists with the given country code and phone number, false otherwise
     */
    boolean existsByCountryCodeAndLineNumber(String countryCode, String lineNumber);

}
