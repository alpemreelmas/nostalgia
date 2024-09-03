package org.nostalgia.auth.util.exception;

import org.ays.common.util.exception.AysAuthException;
import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * An exception that is thrown when a userId is not valid.
 * Extends {@link NostalgiaAuthException}.
 */
public final class NostalgiaUserIdNotValidException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -1446349672040126110L;

    /**
     * Constructs a new {@link NostalgiaUserIdNotValidException} with a default message.
     */
    public NostalgiaUserIdNotValidException(final String userId) {
        super("user id is not valid! userId: " + userId);
    }

}
