package org.nostalgia.auth.util.exception;


import org.nostalgia.auth.model.enums.NostalgiaSourcePage;
import org.nostalgia.common.util.exception.NostalgiaAuthException;

import java.io.Serial;

/**
 * Exception thrown when a user attempts to access a page or resource they are not authorized to view.
 * <p>
 * This exception is used to signify that the user identified by {@code userId} is not permitted
 * to access the specified {@link NostalgiaSourcePage}.
 * </p>
 * <p>
 * It extends {@link NostalgiaAuthException}, which provides the base exception for all authentication-related issues in the AYS application.
 * </p>
 *
 * @see NostalgiaAuthException
 */
public final class NostalgiaUserDoesNotAccessPageException extends NostalgiaAuthException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -1433927234948182106L;

    /**
     * Constructs a new {@code UserDoesNotAccessPageException} with a detailed message
     * indicating the user ID and the source page that the user attempted to access.
     *
     * @param userId     the ID of the user who is attempting unauthorized access
     * @param sourcePage the page or resource the user attempted to access
     */
    public NostalgiaUserDoesNotAccessPageException(String userId, NostalgiaSourcePage sourcePage) {
        super("user not allowed to access the source page! userId:" + userId + " sourcePage:" + sourcePage);
    }

}
