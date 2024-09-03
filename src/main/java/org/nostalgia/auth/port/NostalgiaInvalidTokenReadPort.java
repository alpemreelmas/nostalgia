package org.nostalgia.auth.port;


import org.nostalgia.auth.model.NostalgiaInvalidToken;

import java.util.Optional;

/**
 * A port interface for reading {@link NostalgiaInvalidToken}.
 * Defines methods to retrieve {@link NostalgiaInvalidToken} instances based on ID or token ID.
 */
public interface NostalgiaInvalidTokenReadPort {

    /**
     * Retrieves an {@link NostalgiaInvalidToken} by its token ID.
     *
     * @param tokenId The token ID of the {@link NostalgiaInvalidToken} to retrieve.
     * @return An {@link Optional} containing the found {@link NostalgiaInvalidToken}, or empty if not found.
     */
    Optional<NostalgiaInvalidToken> findByTokenId(String tokenId);

}
