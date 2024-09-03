package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception thrown when attempting to authenticate a user that is not active.
 */
public final class NostalgiaUserNotActiveException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -5218287176856317070L;

    /**
     * Constructs a new UserNotActiveException with the specified userId.
     *
     * @param userId the userId of the user that is not active
     */
    public NostalgiaUserNotActiveException(String userId) {
        super("user is not active! userId:" + userId);
    }

}
