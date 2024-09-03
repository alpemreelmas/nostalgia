package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception to be thrown when a user has a status which is not active or passive.
 */
public final class NostalgiaUserIsNotActiveOrPassiveException extends NostalgiaAlreadyException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 2989379366947338524L;

    /**
     * Constructs a new {@link NostalgiaUserIsNotActiveOrPassiveException} with the specified user id.
     *
     * @param id the id of the user with the status not active or passive.
     */
    public NostalgiaUserIsNotActiveOrPassiveException(String id) {
        super("user status is not active or passive! id:" + id);
    }

}
