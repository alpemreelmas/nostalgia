package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception thrown when a user is passive and attempting to perform an action that requires a passive user.
 */
public final class NostalgiaUserAlreadyPassiveException extends NostalgiaAlreadyException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = -3686691276790127586L;

    /**
     * Constructs a new {@link NostalgiaUserAlreadyPassiveException} with the specified id.
     *
     * @param id the id of the passive user
     */
    public NostalgiaUserAlreadyPassiveException(String id) {
        super("user is already passive! id:" + id);
    }

}
