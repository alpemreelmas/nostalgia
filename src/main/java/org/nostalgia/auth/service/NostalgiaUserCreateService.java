package org.nostalgia.auth.service;


import org.nostalgia.auth.model.request.NostalgiaUserCreateRequest;

/**
 * Service interface for creating users.
 * <p>
 * This interface defines the contract for the user creation functionality. Implementations of this interface
 * are responsible for handling the creation of a new user based on the provided {@link NostalgiaUserCreateRequest}.
 * </p>
 * <p>
 * Implementing classes should ensure the necessary validations and business rules are enforced during the
 * creation process.
 * </p>
 */
public interface NostalgiaUserCreateService {

    /**
     * Creates a new user based on the provided request data.
     * <p>
     * This method handles the creation of a new user by processing the {@link NostalgiaUserCreateRequest} object.
     * Implementations should perform the necessary validations, handle data persistence, and any additional
     * business logic required for creating a user.
     * </p>
     *
     * @param createRequest the request object containing the details for the new user
     */
    void create(NostalgiaUserCreateRequest createRequest);

}
