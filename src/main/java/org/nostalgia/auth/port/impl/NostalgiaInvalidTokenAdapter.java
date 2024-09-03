package org.nostalgia.auth.port.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaInvalidToken;
import org.nostalgia.auth.model.entity.NostalgiaInvalidTokenEntity;
import org.nostalgia.auth.model.mapper.NostalgiaInvalidTokenEntityToDomainMapper;
import org.nostalgia.auth.model.mapper.NostalgiaInvalidTokenToEntityMapper;
import org.nostalgia.auth.port.NostalgiaInvalidTokenDeletePort;
import org.nostalgia.auth.port.NostalgiaInvalidTokenReadPort;
import org.nostalgia.auth.port.NostalgiaInvalidTokenSavePort;
import org.nostalgia.auth.repository.NostalgiaInvalidTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Adapter class that implements read, save, and delete ports for handling {@link NostalgiaInvalidToken} entities.
 * This component interacts with the {@link NostalgiaInvalidTokenRepository} to perform database operations.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaInvalidTokenAdapter implements NostalgiaInvalidTokenReadPort, NostalgiaInvalidTokenSavePort, NostalgiaInvalidTokenDeletePort {

    private final NostalgiaInvalidTokenRepository invalidTokenRepository;


    private final NostalgiaInvalidTokenEntityToDomainMapper invalidTokenEntityToDomainMapper = NostalgiaInvalidTokenEntityToDomainMapper.initialize();
    private final NostalgiaInvalidTokenToEntityMapper invalidTokenToEntityMapper = NostalgiaInvalidTokenToEntityMapper.initialize();


    /**
     * Retrieves an {@link NostalgiaInvalidToken} by its token ID.
     *
     * @param tokenId The token ID of the {@link NostalgiaInvalidToken} to retrieve.
     * @return An {@link Optional} containing the found {@link NostalgiaInvalidToken}, or empty if not found.
     */
    @Override
    public Optional<NostalgiaInvalidToken> findByTokenId(final String tokenId) {
        final Optional<NostalgiaInvalidTokenEntity> invalidTokenEntity = invalidTokenRepository.findByTokenId(tokenId);
        return invalidTokenEntity.map(invalidTokenEntityToDomainMapper::map);
    }

    /**
     * Saves a set of {@link NostalgiaInvalidToken} entities to the database.
     *
     * @param invalidTokens The set of {@link NostalgiaInvalidToken} entities to save.
     */
    @Override
    @Transactional
    public void saveAll(final Set<NostalgiaInvalidToken> invalidTokens) {
        final List<NostalgiaInvalidTokenEntity> invalidTokenEntities = invalidTokenToEntityMapper.map(invalidTokens);
        invalidTokenRepository.saveAll(invalidTokenEntities);
    }

    /**
     * Deletes all {@link NostalgiaInvalidToken} entities from the database that were created before the specified expiration threshold.
     *
     * @param expirationThreshold The timestamp threshold before which {@link NostalgiaInvalidToken} entities will be deleted.
     */
    @Override
    @Transactional
    public void deleteAllByCreatedAtBefore(LocalDateTime expirationThreshold) {
        invalidTokenRepository.deleteAllByCreatedAtBefore(expirationThreshold);
    }

}
