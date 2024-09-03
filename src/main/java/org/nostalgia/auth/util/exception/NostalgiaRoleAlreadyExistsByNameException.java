package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception to be thrown when a role with a given name already exists.
 */
public final class NostalgiaRoleAlreadyExistsByNameException extends NostalgiaAlreadyException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -8192753469109678221L;

    /**
     * Constructs a new NostalgiaRoleAlreadyExistsByNameException with the specified role name.
     *
     * @param name the name of the role that already exists
     */
    public NostalgiaRoleAlreadyExistsByNameException(String name) {
        super("role already exist! name:" + name);
    }

}
