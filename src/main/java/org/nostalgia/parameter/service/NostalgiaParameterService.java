package org.nostalgia.parameter.service;

import org.nostalgia.parameter.model.NostalgiaParameter;

import java.util.Set;

/**
 * A service interface for managing AysParameters.
 */
public interface NostalgiaParameterService {

    /**
     * Retrieves a set of AysParameters that their name starts with the specified prefix.
     *
     * @param prefixOfName the prefix of the name to search for
     * @return a set of AysParameter entities
     */
    Set<NostalgiaParameter> findAll(String prefixOfName);

    /**
     * Retrieves an AysParameter that has the specified name.
     *
     * @param name the name to search for
     * @return an AysParameter
     */
    NostalgiaParameter findByName(String name);

}
