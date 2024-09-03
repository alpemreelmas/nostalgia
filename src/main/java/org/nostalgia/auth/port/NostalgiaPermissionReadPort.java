package org.nostalgia.auth.port;


import org.nostalgia.auth.model.NostalgiaPermission;

import java.util.List;
import java.util.Set;

/**
 * Port interface for reading operations related to permissions.
 */
public interface NostalgiaPermissionReadPort {

    /**
     * Retrieves all permissions.
     *
     * @return A list containing all permissions.
     */
    List<NostalgiaPermission> findAll();

    /**
     * Retrieves all permissions where the 'isSuper' flag is false.
     *
     * @return A list containing permissions where 'isSuper' is false.
     */
    List<NostalgiaPermission> findAllByIsSuperFalse();

    /**
     * Retrieves all permissions by their IDs.
     *
     * @param permissionIds The set of permission IDs to retrieve.
     * @return A list containing permissions with the specified IDs.
     */
    List<NostalgiaPermission> findAllByIds(Set<String> permissionIds);

}
