package org.nostalgia.auth.repository;

import org.nostalgia.auth.model.entity.NostalgiaPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link NostalgiaPermissionEntity} instances.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link NostalgiaPermissionEntity} objects.
 */
public interface NostalgiaPermissionRepository extends JpaRepository<NostalgiaPermissionEntity, String> {

    /**
     * Retrieves all permissions that are not marked as super.
     *
     * @return a list of {@link NostalgiaPermissionEntity} objects that are not marked as super
     */
    List<NostalgiaPermissionEntity> findAllByIsSuperFalse();

}
