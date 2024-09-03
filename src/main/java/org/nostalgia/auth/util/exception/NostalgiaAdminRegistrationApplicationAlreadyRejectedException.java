package org.nostalgia.auth.util.exception;

import org.ays.common.util.exception.AysAlreadyException;

import java.io.Serial;

/**
 * Exception indicating that an admin registration application is already rejected.
 * This exception is a subclass of {@link AysAlreadyException}, which is typically used to indicate that an entity or
 * resource already exists with expected behaviour.
 * Typically, this exception is thrown when a rejection operation is performed on an admin register application
 * entity to an already rejected admin register application.
 */
public class NostalgiaAdminRegistrationApplicationAlreadyRejectedException extends AysAlreadyException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = 8056706849244878245L;

    /**
     * Constructs a new {@link NostalgiaAdminRegistrationApplicationAlreadyRejectedException} with the specified applicationId and applicationStatus.
     *
     * @param id the applicationId of admin register application.
     */
    public NostalgiaAdminRegistrationApplicationAlreadyRejectedException(final String id) {

        super("admin registration application was already rejected! id: " + id);
    }

}
