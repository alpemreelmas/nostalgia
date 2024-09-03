package org.nostalgia.auth.port;

import org.nostalgia.auth.model.NostalgiaUser;

/**
 * Port interface for saving and updating user information.
 */
public interface NostalgiaUserSavePort {

    /**
     * Saves or updates the user information.
     *
     * @param user The user object to be saved or updated.
     * @return The saved or updated user object.
     */
    NostalgiaUser save(NostalgiaUser user);

}
