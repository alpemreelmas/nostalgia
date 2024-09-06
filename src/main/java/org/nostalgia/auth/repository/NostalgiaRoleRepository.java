package org.nostalgia.auth.repository;

import org.nostalgia.auth.model.entity.NostalgiaRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing {@link NostalgiaRoleEntity} entities.
 * <p>
 * This repository provides methods to perform CRUD operations and dynamic queries on
 * {@link NostalgiaRoleEntity} objects, including finding roles by institution ID and status, and finding roles by name.
 * </p>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
public interface NostalgiaRoleRepository extends JpaRepository<NostalgiaRoleEntity, String>, JpaSpecificationExecutor<NostalgiaRoleEntity> {

    /**
     * Finds a {@link NostalgiaRoleEntity} by the given role name.
     *
     * @param name the name of the role
     * @return an {@link Optional} containing the {@link NostalgiaRoleEntity} if found, or empty if not found
     */
    Optional<NostalgiaRoleEntity> findByName(String name);

    /**
     * Checks if any users are assigned to the role identified by the given role ID.
     *
     * @param id The ID of the role to check for assigned users.
     * @return true if there are users assigned to the role, false otherwise.
     */
    @Query("SELECT COUNT(user) > 0 FROM NostalgiaUserEntity user JOIN user.roles role WHERE role.id = :id")
    boolean isRoleAssignedToUser(String id);

}
