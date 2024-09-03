package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception thrown when a token is not valid.
 */
public final class NostalgiaTokenNotValidException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -5404410121820902017L;

    /**
     * Constructs a new TokenNotValidException with the specified JWT and cause.
     *
     * @param jwt   The JWT (JSON Web Token) that is not valid.
     * @param cause The cause of the exception.
     */
    public NostalgiaTokenNotValidException(String jwt, Throwable cause) {
        super("token is not valid! token: " + jwt, cause);
    }

}
