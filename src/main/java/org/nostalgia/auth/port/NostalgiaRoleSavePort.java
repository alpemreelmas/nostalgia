package org.nostalgia.auth.port;

import org.nostalgia.auth.model.NostalgiaRole;

/**
 * Port interface for saving operations related to roles.
 */
public interface NostalgiaRoleSavePort {

    /**
     * Saves a role entity.
     *
     * @param role The role entity to be saved.
     * @return The saved role entity.
     */
    NostalgiaRole save(NostalgiaRole role);

}
