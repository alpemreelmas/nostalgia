package org.nostalgia.common.util.exception;

import java.io.Serial;

/**
 * A base class for exceptions that occur when an entity is expected to exist, but is not found.
 */
public abstract class NostalgiaNotExistException extends RuntimeException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -3046469863777315718L;

    /**
     * Constructs a new {@link NostalgiaNotExistException} with the specified detail message.
     *
     * @param message the detail message.
     */
    protected NostalgiaNotExistException(final String message) {
        super(message);
    }

}
