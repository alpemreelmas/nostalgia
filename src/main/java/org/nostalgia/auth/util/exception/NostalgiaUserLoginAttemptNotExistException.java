package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;

/**
 * Exception to be thrown when a user with a given User ID does not exist.
 */
public final class NostalgiaUserLoginAttemptNotExistException extends NostalgiaNotExistException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 4835784446413089860L;

    /**
     * Constructs a new AysUserNotExistByIdException with the specified user ID.
     *
     * @param userId the User ID of the user that does not exist
     */
    public NostalgiaUserLoginAttemptNotExistException(String userId) {
        super("user login attempt not exist! userId:" + userId);
    }

}
