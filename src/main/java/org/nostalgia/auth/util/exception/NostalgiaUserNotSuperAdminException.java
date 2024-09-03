package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaBadRequestException;

import java.io.Serial;

/**
 * Exception to be thrown when a user is not a super admin but tries to perform an action that requires super admin privileges.
 */
public final class NostalgiaUserNotSuperAdminException extends NostalgiaBadRequestException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -2209124211644478126L;

    /**
     * Constructs a new NostalgiaUserNotSuperAdminException with the specified user ID.
     *
     * @param id the ID of the user who is not a super admin
     */
    public NostalgiaUserNotSuperAdminException(String id) {
        super("user is not super admin! id:" + id);
    }

}
