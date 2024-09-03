package org.nostalgia.auth.service;


import org.nostalgia.auth.model.NostalgiaPermission;

import java.util.List;

/**
 * Service interface for managing permissions.
 * Defines operations to retrieve permissions.
 */
public interface NostalgiaPermissionService {

    /**
     * Retrieves all permissions.
     *
     * @return A list of {@link NostalgiaPermission} objects representing all permissions in the system.
     */
    List<NostalgiaPermission> findAll();

}
