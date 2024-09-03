package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.NostalgiaUserFilter;
import org.nostalgia.auth.model.request.NostalgiaUserListRequest;
import org.nostalgia.auth.port.NostalgiaUserReadPort;
import org.nostalgia.auth.service.NostalgiaUserReadService;
import org.nostalgia.auth.util.exception.NostalgiaUserNotExistByIdException;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation for reading users.
 * <p>
 * The {@code NostalgiaUserReadServiceImpl} class provides the logic for retrieving users based on the provided request
 * and ensures that the users retrieved are scoped to the institution of the current user.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaUserReadServiceImpl implements NostalgiaUserReadService {

    private final NostalgiaUserReadPort userReadPort;

    private final NostalgiaIdentity identity;


    /**
     * Retrieves a paginated list of users based on the provided request.
     * <p>
     * If no filter is provided in the request, a default filter is applied which scopes the results
     * to the institution associated with the current user.
     * </p>
     *
     * @param listRequest the request containing pagination and filtering information.
     * @return a paginated list of users.
     */
    @Override
    public NostalgiaPage<NostalgiaUser> findAll(NostalgiaUserListRequest listRequest) {

        final NostalgiaPageable NostalgiaPageable = listRequest.getPageable();

        return userReadPort.findAll(NostalgiaPageable, listRequest.getFilter());
    }

    /**
     * Retrieves a user by its unique identifier.
     * <p>
     * If the user with the specified ID does not exist, an {@link NostalgiaUserNotExistByIdException} is thrown.
     * </p>
     *
     * @param id the unique identifier of the user.
     * @return the user with the specified ID.
     * @throws NostalgiaUserNotExistByIdException if the user with the specified ID does not exist.
     */
    @Override
    public NostalgiaUser findById(String id) {
        return userReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaUserNotExistByIdException(id));
    }

}
