package org.nostalgia.auth.port;

import org.nostalgia.auth.model.NostalgiaInvalidToken;

import java.time.LocalDateTime;

/**
 * A port interface for deleting {@link NostalgiaInvalidToken} based on created timestamp.
 * Defines a method to delete all tokens created before a specified expiration threshold.
 */
public interface NostalgiaInvalidTokenDeletePort {

    /**
     * Deletes all {@link NostalgiaInvalidToken} created before a specified expiration threshold.
     *
     * @param expirationThreshold The timestamp threshold before which all tokens will be deleted.
     */
    void deleteAllByCreatedAtBefore(LocalDateTime expirationThreshold);

}
