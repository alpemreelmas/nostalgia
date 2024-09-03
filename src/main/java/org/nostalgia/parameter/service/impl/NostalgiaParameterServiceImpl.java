package org.nostalgia.parameter.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.parameter.model.NostalgiaParameter;
import org.nostalgia.parameter.port.NostalgiaParameterReadPort;
import org.nostalgia.parameter.service.NostalgiaParameterService;
import org.nostalgia.parameter.util.exception.NostalgiaParameterNotExistException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service implementation for retrieving {@link NostalgiaParameter} entities.
 * Provides methods to fetch {@link NostalgiaParameter} entities based on name or name prefix.
 */
@Service
@RequiredArgsConstructor
class NostalgiaParameterServiceImpl implements NostalgiaParameterService {

    private final NostalgiaParameterReadPort parameterReadPort;


    /**
     * Retrieves a set of {@link NostalgiaParameter} entities that have names starting with the given prefix.
     *
     * @param prefixOfName the prefix of the names to search for
     * @return a set of {@link NostalgiaParameter} entities with names starting with the given prefix
     */
    @Override
    public Set<NostalgiaParameter> findAll(final String prefixOfName) {
        return parameterReadPort.findAll(prefixOfName);
    }


    /**
     * Retrieves an {@link NostalgiaParameter} that has the given name.
     * Throws {@link NostalgiaParameterNotExistException} if no parameter with the given name is found.
     *
     * @param name the name of the {@link NostalgiaParameter} to search for
     * @return the {@link NostalgiaParameter} with the given name
     * @throws NostalgiaParameterNotExistException if the parameter with the given name does not exist
     */
    @Override
    public NostalgiaParameter findByName(final String name) {
        return parameterReadPort.findByName(name)
                .orElseThrow(() -> new NostalgiaParameterNotExistException(name));
    }

}
