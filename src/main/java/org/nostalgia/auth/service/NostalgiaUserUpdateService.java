package org.nostalgia.auth.service;

import org.nostalgia.auth.util.exception.NostalgiaUserNotActiveException;
import org.nostalgia.auth.util.exception.NostalgiaUserNotExistByIdException;
import org.nostalgia.auth.util.exception.NostalgiaUserNotPassiveException;
import org.nostalgia.auth.model.request.NostalgiaUserUpdateRequest;

/**
 * Service interface for updating users.
 * Implementations of this interface should provide the functionality to update an existing user
 * based on the provided update request.
 */
public interface NostalgiaUserUpdateService {

    /**
     * Updates the user with the specified ID based on the provided update request.
     */
    void update(String id, NostalgiaUserUpdateRequest updateRequest);

    /**
     * Activates a user by ID if the user is currently passive.
     *
     * @param id The unique identifier of the user to be activated.
     * @throws NostalgiaUserNotExistByIdException if a user with the given ID does not exist.
     * @throws NostalgiaUserNotPassiveException   if the user is not in a passive state.
     */
    void activate(String id);

    /**
     * Passivates (deactivates) a user by ID if the user is currently active.
     *
     * @param id The unique identifier of the user to be passivated.
     * @throws NostalgiaUserNotExistByIdException if a user with the given ID does not exist.
     * @throws NostalgiaUserNotActiveException    if the user is not in an active state.
     */
    void passivate(String id);

    /**
     * Deletes a user by its unique identifier.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    void delete(String id);

}
