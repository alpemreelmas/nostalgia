package org.nostalgia.auth.service;


import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.request.NostalgiaRoleListRequest;
import org.nostalgia.common.model.NostalgiaPage;

import java.util.List;

/**
 * Service interface for reading and retrieving roles in a paginated format.
 * <p>
 * The {@link NostalgiaRoleReadService} provides a method for retrieving a paginated list of roles
 * based on the specified request parameters. Implementations of this interface should handle
 * the retrieval of roles, including any necessary filtering, sorting, and pagination logic
 * as defined in the {@link NostalgiaRoleListRequest}.
 * </p>
 *
 * @see NostalgiaRole
 * @see NostalgiaRoleListRequest
 * @see NostalgiaPage
 */
public interface NostalgiaRoleReadService {

    /**
     * Retrieves a paginated list of roles based on the specified {@link NostalgiaRoleListRequest}.
     * <p>
     * This method handles the retrieval of roles from a data source according to the parameters
     * defined in the {@code listRequest}. It typically involves filtering, sorting, and paginating
     * the results to match the request criteria.
     * </p>
     *
     * @param listRequest the request containing parameters for filtering, sorting, and pagination.
     * @return a paginated list of roles matching the request criteria.
     */
    NostalgiaPage<NostalgiaRole> findAll(NostalgiaRoleListRequest listRequest);

    /**
     * Retrieves the details of a specific role by its ID.
     *
     * @param id The ID of the role.
     * @return The role with the specified ID, or null if not found.
     */
    NostalgiaRole findById(String id);

}
