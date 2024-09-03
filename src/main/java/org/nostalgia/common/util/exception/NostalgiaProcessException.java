package org.nostalgia.common.util.exception;

import java.io.Serial;

/**
 * A base class for exceptions that occur during a process execution in the AYS system.
 */
public abstract class NostalgiaProcessException extends RuntimeException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 7261195193622257525L;

    /**
     * Constructs a new {@link NostalgiaProcessException} with the specified detail message.
     *
     * @param message the detail message.
     */
    protected NostalgiaProcessException(final String message) {
        super(message);
    }

}
