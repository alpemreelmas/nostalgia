package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception thrown when a role is deleted and attempting to perform an action that requires a deleted role.
 */
public class NostalgiaRoleAssignedToUserException extends NostalgiaAlreadyException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 7298457193066796371L;

    /**
     * Constructs a new {@link NostalgiaRoleAssignedToUserException} with the specified detail message.
     *
     * @param id the detail message.
     */
    public NostalgiaRoleAssignedToUserException(String id) {
        super("the role is assigned to one or more users id:" + id);
    }

}
