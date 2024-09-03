package org.nostalgia.auth.service;

import org.nostalgia.auth.model.request.NostalgiaRoleUpdateRequest;

/**
 * Service interface for updating roles.
 * Implementations of this interface should provide functionality to update an existing role
 * based on the provided update request.
 */
public interface NostalgiaRoleUpdateService {

    /**
     * Service interface for updating roles.
     * Implementations of this interface should provide functionality to update an existing role
     * based on the provided update request.
     */
    void update(String id, NostalgiaRoleUpdateRequest updateRequest);

    /**
     * Service interface for activating roles.
     * Implementations of this interface should provide functionality to activate an existing role
     * based on the provided id.
     */
    void activate(String id);

    /**
     * Service interface for passivating roles.
     * Implementations of this interface should provide functionality to passivate an existing role
     * based on the provided id.
     */
    void passivate(String id);

    /**
     * Deletes a role by its unique identifier.
     *
     * @param id The unique identifier of the role to be deleted.
     */
    void delete(String id);

}
