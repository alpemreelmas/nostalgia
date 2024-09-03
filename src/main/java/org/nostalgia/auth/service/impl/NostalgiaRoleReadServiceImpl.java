package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.NostalgiaRoleFilter;
import org.nostalgia.auth.model.request.NostalgiaRoleListRequest;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.service.NostalgiaRoleReadService;
import org.nostalgia.auth.util.exception.NostalgiaRoleNotExistByIdException;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for reading roles.
 * <p>
 * The {@code NostalgiaRoleReadServiceImpl} class provides the logic for retrieving roles based on the provided request
 * and ensures that the roles retrieved are scoped to the institution of the current user.
 * </p>
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class NostalgiaRoleReadServiceImpl implements NostalgiaRoleReadService {

    private final NostalgiaRoleReadPort roleReadPort;

    private final NostalgiaIdentity identity;


    /**
     * Retrieves all roles.
     * <p>
     * This method retrieves all roles from the data source and returns them as a set.
     * </p>
     *
     * @return a list of {@link NostalgiaRole} objects representing all roles.
     */
    @Override
    public List<NostalgiaRole> findAll() {
        return roleReadPort.findAllActivesByInstitutionId(identity.getInstitutionId());
    }


    /**
     * Retrieves a paginated list of roles based on the provided request.
     * <p>
     * If no filter is provided in the request, a default filter is applied which scopes the results
     * to the institution associated with the current user.
     * </p>
     *
     * @param listRequest the request containing pagination and filtering information.
     * @return a paginated list of roles.
     */
    @Override
    public NostalgiaPage<NostalgiaRole> findAll(final NostalgiaRoleListRequest listRequest) {

        final NostalgiaPageable NostalgiaPageable = listRequest.getPageable();

        Optional.ofNullable(listRequest.getFilter())
                .ifPresentOrElse(filter -> {
                            if (filter.getInstitutionId() == null) {
                                filter.setInstitutionId(identity.getInstitutionId());
                            }
                        },
                        () -> {
                            NostalgiaRoleFilter filter = NostalgiaRoleFilter.builder()
                                    .institutionId(identity.getInstitutionId())
                                    .build();

                            listRequest.setFilter(filter);
                        });

        return roleReadPort.findAll(NostalgiaPageable, listRequest.getFilter());
    }


    /**
     * Retrieves a role by its unique identifier.
     * <p>
     * If the role with the specified ID does not exist or the role's institution ID does not match the user's
     * institution ID, an {@link NostalgiaRoleNotExistByIdException} is thrown.
     * </p>
     *
     * @param id the unique identifier of the role.
     * @return the role with the specified ID.
     * @throws NostalgiaRoleNotExistByIdException if the role with the specified ID does not exist.
     */
    @Override
    public NostalgiaRole findById(String id) {
        return roleReadPort.findById(id)
                .orElseThrow(() -> new NostalgiaRoleNotExistByIdException(id));
    }

}
