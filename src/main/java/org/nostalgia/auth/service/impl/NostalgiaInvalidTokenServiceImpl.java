package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaInvalidToken;
import org.nostalgia.auth.port.NostalgiaInvalidTokenReadPort;
import org.nostalgia.auth.port.NostalgiaInvalidTokenSavePort;
import org.nostalgia.auth.service.NostalgiaInvalidTokenService;
import org.nostalgia.auth.util.exception.NostalgiaTokenAlreadyInvalidatedException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link NostalgiaInvalidTokenService} interface for managing invalid tokens within the application.
 * <p>
 * This service class provides methods to invalidate tokens and check if a token has already been invalidated.
 * It uses ports to read and save invalid tokens, ensuring that token management operations are handled efficiently.
 * </p>
 */
@Service
@RequiredArgsConstructor
class NostalgiaInvalidTokenServiceImpl implements NostalgiaInvalidTokenService {

    private final NostalgiaInvalidTokenReadPort invalidTokenReadPort;
    private final NostalgiaInvalidTokenSavePort invalidTokenSavePort;

    /**
     * Invalidates multiple tokens by saving them as invalid tokens in the system.
     * <p>
     * This method converts each token ID into an {@link NostalgiaInvalidToken} object and saves them using
     * the {@link NostalgiaInvalidTokenSavePort}.
     * </p>
     *
     * @param tokenIds the set of token IDs to invalidate
     */
    @Override
    public void invalidateTokens(final Set<String> tokenIds) {
        final Set<NostalgiaInvalidToken> invalidTokens = tokenIds.stream()
                .map(tokenId -> NostalgiaInvalidToken.builder()
                        .tokenId(tokenId)
                        .build()
                )
                .collect(Collectors.toSet());

        invalidTokenSavePort.saveAll(invalidTokens);
    }

    /**
     * Checks if a token has already been invalidated.
     * <p>
     * This method queries the {@link NostalgiaInvalidTokenReadPort} to determine if the specified token ID
     * exists as an invalidated token. If it does, an {@link NostalgiaTokenAlreadyInvalidatedException} is thrown.
     * </p>
     *
     * @param tokenId the token ID to check for invalidity
     * @throws NostalgiaTokenAlreadyInvalidatedException if the token has already been invalidated
     */
    @Override
    public void checkForInvalidityOfToken(final String tokenId) {
        final boolean isTokenInvalid = invalidTokenReadPort.findByTokenId(tokenId).isPresent();
        if (isTokenInvalid) {
            throw new NostalgiaTokenAlreadyInvalidatedException(tokenId);
        }
    }

}
