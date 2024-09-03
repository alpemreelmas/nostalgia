package org.nostalgia.auth.util.exception;


import org.nostalgia.common.util.exception.NostalgiaNotExistException;

import java.io.Serial;
import java.util.List;

/**
 * Exception to be thrown when a permission with a given ID does not exist.
 */
public final class NostalgiaPermissionNotExistException extends NostalgiaNotExistException {

    /**
     * Unique serial version ID.
     */
    @Serial
    private static final long serialVersionUID = 1022212670473308130L;

    /**
     * Constructs a new NostalgiaPermissionNotExistException with the specified permission IDs.
     *
     * @param ids the IDs of the permissions don't exist
     */
    public NostalgiaPermissionNotExistException(List<String> ids) {
        super("the following permissions were not found! permissionIds:" + ids);
    }

}
