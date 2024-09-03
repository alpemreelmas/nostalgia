package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception thrown when a user password cannot be changed.
 */
public final class NostalgiaUserPasswordCannotChangedException extends NostalgiaAuthException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = -2214328005741759939L;

    /**
     * Constructs a new {@link NostalgiaUserPasswordCannotChangedException} with the given password ID.
     *
     * @param passwordId the ID of the password that cannot be changed
     */
    public NostalgiaUserPasswordCannotChangedException(String passwordId) {
        super("user password cannot be changed! passwordId:" + passwordId);
    }

}
