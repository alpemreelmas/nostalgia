package org.nostalgia.auth.service;

import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.request.NostalgiaUserListRequest;
import org.nostalgia.common.model.NostalgiaPage;

/**
 * Service interface for reading and retrieving users in a paginated format.
 * <p>
 * The {@link NostalgiaUserReadService} provides a method for retrieving a paginated list of users
 * based on the specified request parameters. Implementations of this interface should handle
 * the retrieval of users, including any necessary filtering, sorting, and pagination logic
 * as defined in the {@link NostalgiaUserListRequest}.
 * </p>
 *
 * @see NostalgiaUser
 * @see NostalgiaUserListRequest
 * @see NostalgiaPage
 */
public interface NostalgiaUserReadService {

    /**
     * Retrieves a paginated list of users based on the specified {@link NostalgiaUserListRequest}.
     * <p>
     * This method handles the retrieval of users from a data source according to the parameters
     * defined in the {@code listRequest}. It typically involves filtering, sorting, and paginating
     * the results to match the request criteria.
     * </p>
     *
     * @param listRequest the request containing parameters for filtering, sorting, and pagination.
     * @return a paginated list of users matching the request criteria.
     */
    NostalgiaPage<NostalgiaUser> findAll(NostalgiaUserListRequest listRequest);

    /**
     * Retrieves the details of a specific user by its ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID, or null if not found.
     */
    NostalgiaUser findById(String id);

}
