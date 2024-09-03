package org.nostalgia.auth.port;


import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.NostalgiaUserFilter;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;
import org.nostalgia.common.model.NostalgiaPhoneNumber;

import java.util.Optional;

/**
 * Port interface for reading operations related to {@link NostalgiaUser}.
 */
public interface NostalgiaUserReadPort {

    /**
     * Finds all users with pagination and optional filtering.
     *
     * @param aysPageable the pagination configuration
     * @param filter      the filter for users
     * @return a paginated list of users
     */
    NostalgiaPage<NostalgiaUser> findAll(NostalgiaPageable aysPageable, NostalgiaUserFilter filter);

    /**
     * Retrieves a {@link NostalgiaUser} by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An optional containing the {@link NostalgiaUser} if found, otherwise empty.
     */
    Optional<NostalgiaUser> findById(String id);

    /**
     * Retrieves a {@link NostalgiaUser} by its email address.
     *
     * @param emailAddress The email address of the user to retrieve.
     * @return An optional containing the {@link NostalgiaUser} if found, otherwise empty.
     */
    Optional<NostalgiaUser> findByEmailAddress(String emailAddress);

    /**
     * Finds a user by their phone number, which is a concatenation of country code and line number.
     *
     * @param phoneNumber the concatenated phone number (country code + line number) of the user to be found
     * @return an optional containing the AysUser with the given phone number, or an empty optional if not found
     */
    Optional<NostalgiaUser> findByPhoneNumber(NostalgiaPhoneNumber phoneNumber);

    /**
     * Finds a user by their password ID.
     *
     * @param passwordId the ID of the password to search for.
     * @return an Optional containing the found user, or empty if no user was found with the given password ID.
     */
    Optional<NostalgiaUser> findByPasswordId(String passwordId);

    /**
     * Checks if a user with the given email address exists in the repository.
     *
     * @param emailAddress The email address to check for existence.
     * @return true if a user with the given email address exists, otherwise false.
     */
    boolean existsByEmailAddress(String emailAddress);

    /**
     * Checks if a user with the given phone number exists in the repository.
     *
     * @param phoneNumber The phone number to check for existence.
     * @return true if a user with the given phone number exists, otherwise false.
     */
    boolean existsByPhoneNumber(NostalgiaPhoneNumber phoneNumber);


}
