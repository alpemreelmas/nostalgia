package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;

/**
 * Exception to be thrown when a user with a given ID does not exist.
 */
public final class NostalgiaUserNotExistByIdException extends NostalgiaNotExistException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 7236202847233201524L;

    /**
     * Constructs a new NostalgiaUserNotExistByIdException with the specified user ID.
     *
     * @param id the ID of the user that does not exist
     */
    public NostalgiaUserNotExistByIdException(String id) {
        super("user not exist! id:" + id);
    }

}
