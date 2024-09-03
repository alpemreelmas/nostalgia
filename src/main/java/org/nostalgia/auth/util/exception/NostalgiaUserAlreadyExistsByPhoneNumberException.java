package org.nostalgia.auth.util.exception;

import org.nostalgia.common.model.NostalgiaPhoneNumber;
import org.nostalgia.common.util.exception.NostalgiaAlreadyException;

import java.io.Serial;

/**
 * This exception is thrown when attempting to create a user with a phone number that already exists in the system.
 */
public final class NostalgiaUserAlreadyExistsByPhoneNumberException extends NostalgiaAlreadyException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 259255128392056805L;

    /**
     * Constructs a new AysUserAlreadyExistsByPhoneNumberException with the specified phone number.
     *
     * @param phoneNumber The phone number that already exists in the system.
     */
    public NostalgiaUserAlreadyExistsByPhoneNumberException(NostalgiaPhoneNumber phoneNumber) {
        super("user already exist! countryCode:" + phoneNumber.getCountryCode() + " , " + "lineNumber:" + phoneNumber.getLineNumber());
    }

}
