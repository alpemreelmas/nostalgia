package org.nostalgia.auth.util.exception;


import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;

/**
 * Exception to be thrown when a role's status is invalid for the requested operation.
 */
public final class NostalgiaInvalidRoleStatusException extends NostalgiaNotExistException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -933312733826008379L;

    /**
     * Constructs a new {@link NostalgiaInvalidRoleStatusException} with the specified role status.
     *
     * @param status the invalid status of the role.
     */
    public NostalgiaInvalidRoleStatusException(NostalgiaRoleStatus status) {
        super("role status is not " + status.toString().toLowerCase() + "!");
    }

}
