package org.nostalgia.parameter.repository;

import org.nostalgia.parameter.model.entity.NostalgiaParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for performing CRUD operations on {@link NostalgiaParameterEntity} instances.
 * Provides methods for finding {@link NostalgiaParameterEntity} instances by name prefix.
 */
public interface NostalgiaParameterRepository extends JpaRepository<NostalgiaParameterEntity, Long> {

    /**
     * Finds a set of {@link NostalgiaParameterEntity} instances whose names start with the specified prefix.
     *
     * @param prefixOfName the prefix to search for
     * @return a set of {@link NostalgiaParameterEntity} instances whose names start with the specified prefix
     */
    Set<NostalgiaParameterEntity> findByNameStartingWith(String prefixOfName);

    /**
     * Finds an {@link NostalgiaParameterEntity} instances by name.
     *
     * @param name the name to search for
     * @return an {@link Optional} containing the {@link NostalgiaParameterEntity} entity if found, otherwise empty
     */
    Optional<NostalgiaParameterEntity> findByName(String name);

}
