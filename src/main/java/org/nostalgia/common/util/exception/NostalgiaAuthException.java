package org.nostalgia.common.util.exception;

import java.io.Serial;

/**
 * AysAuthException is an abstract base class for exceptions related to authentication.
 * This class extends RuntimeException to indicate that exceptions of this type are
 * unchecked exceptions and do not need to be declared in a method or constructor's
 * throws clause.
 */
public abstract class NostalgiaAuthException extends RuntimeException {

    /**
     * The serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 2451728277403347321L;

    /**
     * Constructs a new AysAuthException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    protected NostalgiaAuthException(final String message) {
        super(message);
    }

    protected NostalgiaAuthException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
