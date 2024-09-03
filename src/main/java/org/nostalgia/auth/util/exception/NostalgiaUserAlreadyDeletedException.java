package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception thrown when a user is deleted and attempting to perform an action that requires a deleted user.
 */
public final class NostalgiaUserAlreadyDeletedException extends NostalgiaAlreadyException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 2187171739927065907L;

    /**
     * Constructs a new {@link NostalgiaUserAlreadyDeletedException} with the specified id.
     *
     * @param id the id of the deleted user
     */
    public NostalgiaUserAlreadyDeletedException(String id) {
        super("user is already deleted! id:" + id);
    }

}
