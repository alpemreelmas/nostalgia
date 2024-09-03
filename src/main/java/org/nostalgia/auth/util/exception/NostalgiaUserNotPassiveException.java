package org.nostalgia.auth.util.exception;


import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;

/**
 Exception thrown when a user does not in a passive state.
 * This exception extends {@link NostalgiaNotExistException}.
 */
public final class NostalgiaUserNotPassiveException extends NostalgiaNotExistException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 3508025652421021710L;

    /**
     * Constructs a new {@link NostalgiaNotExistException} with the specified detail message.
     *
     * @param status the detail message.
     */
    public NostalgiaUserNotPassiveException(NostalgiaUserStatus status) {
        super("user status is not with " + status.toString().toLowerCase() + "!");
    }
}
