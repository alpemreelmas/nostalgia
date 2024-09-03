package org.nostalgia.auth.util.exception;

import org.ays.common.util.exception.AysAuthException;
import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * An exception that is thrown when a emailAddress is not valid.
 * Extends {@link NostalgiaAuthException}.
 */
public final class NostalgiaEmailAddressNotValidException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 8716775556496272471L;

    /**
     * Constructs a new {@link NostalgiaEmailAddressNotValidException} with a default message.
     */
    public NostalgiaEmailAddressNotValidException(final String emailAddress) {
        super("email address is not valid! emailAddress: " + emailAddress);
    }

}
