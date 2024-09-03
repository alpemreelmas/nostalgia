package org.nostalgia.parameter.port.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.parameter.model.NostalgiaParameter;
import org.nostalgia.parameter.model.entity.NostalgiaParameterEntity;
import org.nostalgia.parameter.model.mapper.NostalgiaParameterEntityToDomainMapper;
import org.nostalgia.parameter.port.NostalgiaParameterReadPort;
import org.nostalgia.parameter.repository.NostalgiaParameterRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An adapter class implementing {@link NostalgiaParameterReadPort} for accessing {@link NostalgiaParameter} entities.
 * This class interacts with the underlying repository to fetch the data and uses a mapper
 * to convert entity objects to domain objects.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaParameterAdapter implements NostalgiaParameterReadPort {

    private final NostalgiaParameterRepository parameterRepository;


    private final NostalgiaParameterEntityToDomainMapper parameterEntityToParameterMapper = NostalgiaParameterEntityToDomainMapper.initialize();


    /**
     * Retrieves a set of {@link NostalgiaParameter} entities that have names starting with the given prefix.
     *
     * @param prefixOfName the prefix of the names to search for
     * @return a set of {@link NostalgiaParameter} entities with names starting with the given prefix
     */
    @Override
    public Set<NostalgiaParameter> findAll(final String prefixOfName) {
        return parameterRepository.findByNameStartingWith(prefixOfName).stream()
                .map(parameterEntityToParameterMapper::map)
                .collect(Collectors.toSet());
    }


    /**
     * Retrieves an {@link NostalgiaParameter} entity by its name.
     *
     * @param name the name of the {@link NostalgiaParameter} to search for
     * @return an {@link Optional} containing the {@link NostalgiaParameter} entity if found, otherwise empty
     */
    @Override
    public Optional<NostalgiaParameter> findByName(final String name) {
        final Optional<NostalgiaParameterEntity> parameterEntity = parameterRepository.findByName(name);
        return parameterEntity.map(parameterEntityToParameterMapper::map);
    }

}
