package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception thrown when a role is deleted and attempting to perform an action that requires a deleted role.
 */
public final class NostalgiaRoleAlreadyDeletedException extends NostalgiaAlreadyException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = -7631303999314005771L;

    /**
     * Constructs a new {@link NostalgiaRoleAlreadyDeletedException} with the specified detail message.
     *
     * @param id the id of the deleted user
     */
    public NostalgiaRoleAlreadyDeletedException(String id) {
        super("role is already deleted! id:" + id);
    }

}
