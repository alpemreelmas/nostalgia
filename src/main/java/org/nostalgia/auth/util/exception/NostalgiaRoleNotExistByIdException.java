package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;

/**
 * Exception to be thrown when a role with a given ID does not exist.
 */
public final class NostalgiaRoleNotExistByIdException extends NostalgiaNotExistException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = -1647644049969376455L;

    /**
     * Constructs a new {@link NostalgiaRoleNotExistByIdException} with the specified role ID.
     *
     * @param id the ID of the role that does not exist
     */
    public NostalgiaRoleNotExistByIdException(String id) {
        super("role does not exist! id:" + id);
    }

}
