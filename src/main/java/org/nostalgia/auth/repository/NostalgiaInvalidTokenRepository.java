package org.nostalgia.auth.repository;

import org.nostalgia.auth.model.entity.NostalgiaInvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * NostalgiaInvalidTokenRepository is a repository interface that provides methods for accessing and managing invalid token entities.
 */
public interface NostalgiaInvalidTokenRepository extends JpaRepository<NostalgiaInvalidTokenEntity, Long> {

    /**
     * Finds an invalid token entity by its token ID.
     *
     * @param tokenId the ID of the token to search for
     * @return an Optional containing the found invalid token entity, or an empty Optional if not found
     */
    Optional<NostalgiaInvalidTokenEntity> findByTokenId(String tokenId);

    /**
     * Finds all invalid token entities that were created before the specified date.
     *
     * @param createdAt the date to search for
     */
    void deleteAllByCreatedAtBefore(LocalDateTime createdAt);

}
