package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception to be thrown when a password is not valid.
 */
public final class NostalgiaPasswordNotValidException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -6170966118655522879L;

    /**
     * Constructs a new PasswordNotValidException with a default error message.
     */
    public NostalgiaPasswordNotValidException() {
        super("password is not valid!");
    }

}
