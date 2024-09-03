package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception thrown when a user password does not exist.
 */
public final class NostalgiaUserPasswordDoesNotExistException extends NostalgiaAuthException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = -9023497278913148659L;

    /**
     * Constructs a new {@link NostalgiaUserPasswordDoesNotExistException} with the given password ID.
     *
     * @param passwordId the ID of the password that does not exist
     */
    public NostalgiaUserPasswordDoesNotExistException(String passwordId) {
        super("user password does not exist! passwordId:" + passwordId);
    }

}
