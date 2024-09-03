package org.nostalgia.auth.port.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaRole;
import org.nostalgia.auth.model.NostalgiaRoleFilter;
import org.nostalgia.auth.model.entity.NostalgiaRoleEntity;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.auth.model.mapper.NostalgiaRoleEntityToDomainMapper;
import org.nostalgia.auth.model.mapper.NostalgiaRoleToEntityMapper;
import org.nostalgia.auth.port.NostalgiaRoleReadPort;
import org.nostalgia.auth.port.NostalgiaRoleSavePort;
import org.nostalgia.auth.repository.NostalgiaRoleRepository;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Adapter class implementing both {@link NostalgiaRoleReadPort} and {@link NostalgiaRoleSavePort} interfaces.
 * Retrieves {@link NostalgiaRole} entities from the repository, saves {@link NostalgiaRole} entities to the database,
 * and maps between domain models and entity models.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaRoleAdapter implements NostalgiaRoleReadPort, NostalgiaRoleSavePort {

    private final NostalgiaRoleRepository roleRepository;


    private final NostalgiaRoleToEntityMapper roleToEntityMapper = NostalgiaRoleToEntityMapper.initialize();
    private final NostalgiaRoleEntityToDomainMapper roleEntityToDomainMapper = NostalgiaRoleEntityToDomainMapper.initialize();


    /**
     * Finds all roles with pagination and optional filtering.
     * <p>
     * This method uses the provided {@link NostalgiaPageable} for pagination and {@link NostalgiaRoleFilter} for filtering.
     * It returns a paginated list of {@link NostalgiaRole} domain models.
     * </p>
     *
     * @param NostalgiaPageable the pagination configuration
     * @param filter      the filter for roles
     * @return a paginated list of roles
     */
    @Override
    public NostalgiaPage<NostalgiaRole> findAll(final NostalgiaPageable NostalgiaPageable, final NostalgiaRoleFilter filter) {

        final Pageable pageable = NostalgiaPageable.toPageable();

        final Specification<NostalgiaRoleEntity> specification = filter.toSpecification();

        final Page<NostalgiaRoleEntity> roleEntitiesPage = roleRepository.findAll(specification, pageable);

        final List<NostalgiaRole> roles = roleEntityToDomainMapper.map(roleEntitiesPage.getContent());

        return NostalgiaPage.of(filter, roleEntitiesPage, roles);
    }


    /**
     * Retrieves an {@link NostalgiaRole} by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return An {@link Optional} containing the {@link NostalgiaRole} if found, otherwise empty.
     */
    @Override
    public Optional<NostalgiaRole> findById(final String id) {
        final Optional<NostalgiaRoleEntity> roleEntity = roleRepository
                .findById(id);
        return roleEntity.map(roleEntityToDomainMapper::map);
    }


    /**
     * Retrieves all active {@link NostalgiaRole} entities associated with a specific institution ID.
     *
     * @param institutionId The ID of the institution to filter roles by.
     * @return A set of active {@link NostalgiaRole} entities found for the institution.
     */
    @Override
    public List<NostalgiaRole> findAllActivesByInstitutionId(final String institutionId) {
        List<NostalgiaRoleEntity> roleEntities = roleRepository.findAllByInstitutionIdAndStatus(institutionId, NostalgiaRoleStatus.ACTIVE);
        return roleEntityToDomainMapper.map(roleEntities);
    }


    /**
     * Retrieves all {@link NostalgiaRole} entities by their IDs.
     *
     * @param ids A set of role IDs to retrieve.
     * @return A list of {@link NostalgiaRole} entities corresponding to the provided IDs.
     */
    @Override
    public List<NostalgiaRole> findAllByIds(Set<String> ids) {
        List<NostalgiaRoleEntity> roleEntities = roleRepository.findAllById(ids);
        return roleEntityToDomainMapper.map(roleEntities);
    }


    /**
     * Retrieves an {@link NostalgiaRole} by its name.
     *
     * @param name The name of the role to retrieve.
     * @return An optional containing the {@link NostalgiaRole} if found, otherwise empty.
     */
    @Override
    public Optional<NostalgiaRole> findByName(final String name) {
        final Optional<NostalgiaRoleEntity> roleEntity = roleRepository.findByName(name);
        return roleEntity.map(roleEntityToDomainMapper::map);
    }


    /**
     * Checks if any users are assigned to a role identified by its ID.
     *
     * @param id The ID of the role to check.
     * @return true if users are assigned to the role, false otherwise.
     */
    @Override
    public boolean isRoleUsing(String id) {
        return roleRepository.isRoleAssignedToUser(id);
    }


    /**
     * Saves an {@link NostalgiaRole} to the database.
     *
     * @param role The {@link NostalgiaRole} to save.
     * @return The saved {@link NostalgiaRole} after persistence.
     */
    @Override
    @Transactional
    public NostalgiaRole save(final NostalgiaRole role) {
        final NostalgiaRoleEntity roleEntity = roleToEntityMapper.map(role);
        roleRepository.save(roleEntity);
        return roleEntityToDomainMapper.map(roleEntity);
    }

}
