package org.nostalgia.auth.util.exception;

import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * Exception indicating that an user already exists with the specified email address.
 * This exception is a subclass of {@link NostalgiaAlreadyException}.
 */
public final class NostalgiaUserAlreadyExistsByEmailAddressException extends NostalgiaAlreadyException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 5626401282048575054L;

    /**
     * Constructs a new AysUserAlreadyExistsByEmailException with the specified email address.
     *
     * @param emailAddress The email address of the user that already exists.
     */
    public NostalgiaUserAlreadyExistsByEmailAddressException(String emailAddress) {
        super("user already exist! emailAddress:" + emailAddress);
    }

}
