package org.nostalgia.auth.service;

import org.nostalgia.auth.model.request.NostalgiaRoleCreateRequest;

/**
 * Service interface for creating roles.
 * This interface provides a method to create a role using the given request data.
 */
public interface NostalgiaRoleCreateService {

    /**
     * Creates a new role based on the provided {@link NostalgiaRoleCreateRequest}.
     *
     * @param createRequest the request object containing the details for the role to be created
     */
    void create(org.nostalgia.auth.model.request.NostalgiaRoleCreateRequest createRequest);

}
