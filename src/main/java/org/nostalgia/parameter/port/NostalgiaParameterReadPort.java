package org.nostalgia.parameter.port;


import org.nostalgia.parameter.model.NostalgiaParameter;

import java.util.Optional;
import java.util.Set;

/**
 * A read port interface for accessing {@link NostalgiaParameter} data.
 * Defines methods to retrieve {@link NostalgiaParameter} entities based on name or name prefix.
 */
public interface NostalgiaParameterReadPort {

    /**
     * Retrieves a set of {@link NostalgiaParameter} entities whose names start with the given prefix.
     *
     * @param prefixOfName the prefix of the names to search for
     * @return a set of {@link NostalgiaParameter} entities with names starting with the given prefix
     */
    Set<NostalgiaParameter> findAll(String prefixOfName);

    /**
     * Retrieves an {@link NostalgiaParameter} entity by its name.
     *
     * @param name the name of the {@link NostalgiaParameter} to search for
     * @return an {@link Optional} containing the {@link NostalgiaParameter} entity if found, otherwise empty
     */
    Optional<NostalgiaParameter> findByName(String name);

}
