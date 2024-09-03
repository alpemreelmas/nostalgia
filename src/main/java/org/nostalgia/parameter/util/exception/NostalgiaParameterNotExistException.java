package org.nostalgia.parameter.util.exception;


import org.nostalgia.common.util.exception.NostalgiaNotExistException;
import org.nostalgia.parameter.model.NostalgiaParameter;

import java.io.Serial;

/**
 * Exception thrown when a specified {@link NostalgiaParameter} does not exist.
 * Extends {@link NostalgiaNotExistException} to indicate the absence of a parameter entity.
 */
public final class NostalgiaParameterNotExistException extends NostalgiaNotExistException {

    /**
     * Unique identifier for serialization.
     */
    @Serial
    private static final long serialVersionUID = -4279786521082293747L;

    /**
     * Constructs a new {@code NostalgiaParameterNotExistException} with the specified parameter name.
     *
     * @param name the name of the parameter that does not exist
     */
    public NostalgiaParameterNotExistException(String name) {
        super("parameter does not exist! name:" + name);
    }

}
