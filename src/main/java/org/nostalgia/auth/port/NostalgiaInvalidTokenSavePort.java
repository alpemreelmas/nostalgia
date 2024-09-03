package org.nostalgia.auth.port;

import org.nostalgia.auth.model.NostalgiaInvalidToken;

import java.util.Set;

/**
 * A port interface for saving {@link NostalgiaInvalidToken} entities.
 * Defines a method to persist multiple {@link NostalgiaInvalidToken} instances.
 */
public interface NostalgiaInvalidTokenSavePort {

    /**
     * Persists multiple {@link NostalgiaInvalidToken} instances.
     *
     * @param invalidTokens The set of {@link NostalgiaInvalidToken} instances to be saved.
     */
    void saveAll(Set<NostalgiaInvalidToken> invalidTokens);

}
