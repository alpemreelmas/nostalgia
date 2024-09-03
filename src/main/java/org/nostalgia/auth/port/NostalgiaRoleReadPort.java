package org.nostalgia.auth.port;

import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.NostalgiaRoleFilter;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * {@link NostalgiaRoleReadPort} is an interface for reading role data from the underlying data source.
 * <p>
 * It provides methods for retrieving roles based on various criteria such as pagination, filters,
 * institution ID, and role name. This interface is part of the application's hexagonal architecture,
 * facilitating the separation between domain logic and data access logic.
 * </p>
 */
public interface NostalgiaRoleReadPort {

    /**
     * Retrieves a paginated list of roles based on the provided pageable and filter.
     *
     * @param NostalgiaPageable The pagination configuration.
     * @param filter      The filter criteria for the roles.
     * @return A paginated list of roles.
     */
    NostalgiaPage<NostalgiaRole> findAll(NostalgiaPageable NostalgiaPageable, NostalgiaRoleFilter filter);

    /**
     * Retrieves a role by its unique identifier.
     *
     * @param id the unique identifier of the role.
     * @return a role.
     */
    Optional<NostalgiaRole> findById(String id);

    /**
     * Retrieves all active roles associated with a specific institution.
     *
     * @param institutionId The ID of the institution.
     * @return A list of active roles belonging to the institution.
     */
    List<NostalgiaRole> findAllActivesByInstitutionId(String institutionId);

    /**
     * Retrieves all {@link NostalgiaRole} entities for the given set of role IDs.
     *
     * @param ids A set of role IDs for which the roles are to be retrieved.
     * @return A list of {@link NostalgiaRole} entities for the provided IDs.
     */
    List<NostalgiaRole> findAllByIds(Set<String> ids);

    /**
     * Retrieves a role by its name.
     *
     * @param name The name of the role to retrieve.
     * @return An optional containing the role if found, otherwise empty.
     */
    Optional<NostalgiaRole> findByName(String name);

    /**
     * Checks if any users are assigned to a role identified by its ID.
     *
     * @param id The ID of the role to check.
     * @return true if users are assigned to the role, false otherwise.
     */
    boolean isRoleUsing(String id);

}
