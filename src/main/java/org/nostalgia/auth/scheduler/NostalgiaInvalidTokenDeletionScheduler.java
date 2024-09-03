package org.nostalgia.auth.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.nostalgia.auth.model.enums.NostalgiaConfigurationParameter;
import org.nostalgia.auth.port.NostalgiaInvalidTokenDeletePort;
import org.nostalgia.parameter.model.NostalgiaParameter;
import org.nostalgia.parameter.port.NostalgiaParameterReadPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Scheduler component for deleting invalid tokens based on a configured cron expression.
 * <p>
 * This component deletes invalid tokens that were created before a specified expiration threshold,
 * as determined by the configured refresh token expiration day parameter. It uses a {@link NostalgiaInvalidTokenDeletePort}
 * to perform the deletion operation. The scheduler is enabled if the property
 * `Nostalgia.scheduler.invalid-tokens-deletion.enable` is set to `true` in the application properties.
 * </p>
 *
 * <p>
 * The scheduler logs the deletion process at various levels (trace and info) and ensures that unused invalid tokens
 * are cleared periodically to maintain system cleanliness.
 * </p>
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "Nostalgia.scheduler.invalid-tokens-deletion.enable", havingValue = "true")
class NostalgiaInvalidTokenDeletionScheduler {

    private final NostalgiaInvalidTokenDeletePort invalidTokenDeletePort;
    private final NostalgiaParameterReadPort parameterReadPort;

    public NostalgiaInvalidTokenDeletionScheduler(NostalgiaInvalidTokenDeletePort invalidTokenDeletePort,
                                                  NostalgiaParameterReadPort parameterReadPort) {

        this.invalidTokenDeletePort = invalidTokenDeletePort;
        this.parameterReadPort = parameterReadPort;

        log.info("InvalidTokenDeletionScheduler is enabled.");
    }

    /**
     * Scheduled method to delete invalid tokens based on the configured cron expression.
     * <p>
     * This method runs periodically based on the cron expression configured in
     * `Nostalgia.scheduler.invalid-tokens-deletion.cron`. It retrieves the expiration threshold from the application parameters,
     * calculates the threshold date, and deletes all invalid tokens created before that date.
     * </p>
     */
    @Transactional
    @Scheduled(cron = "${Nostalgia.scheduler.invalid-tokens-deletion.cron}")
    public void deleteInvalidTokens() {
        final NostalgiaParameter refreshTokenExpireDayParameter = parameterReadPort
                .findByName(NostalgiaConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY.name())
                .orElse(NostalgiaParameter.from(NostalgiaConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY));

        LocalDateTime expirationThreshold = LocalDateTime.now()
                .minusDays(Long.parseLong(refreshTokenExpireDayParameter.getDefinition()));

        log.trace("Clearing all unused invalid tokens created before {}", expirationThreshold);
        invalidTokenDeletePort.deleteAllByCreatedAtBefore(expirationThreshold);
        log.trace("All unused invalid tokens created before {} have been cleared", expirationThreshold);
    }

}
