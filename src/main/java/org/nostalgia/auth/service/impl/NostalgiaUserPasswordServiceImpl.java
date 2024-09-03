package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.request.NostalgiaPasswordCreateRequest;
import org.nostalgia.auth.model.request.NostalgiaPasswordForgotRequest;
import org.nostalgia.auth.port.NostalgiaUserReadPort;
import org.nostalgia.auth.port.NostalgiaUserSavePort;
import org.nostalgia.auth.service.NostalgiaUserMailService;
import org.nostalgia.auth.service.NostalgiaUserPasswordService;
import org.nostalgia.auth.util.exception.NostalgiaEmailAddressNotValidException;
import org.nostalgia.auth.util.exception.NostalgiaUserPasswordCannotChangedException;
import org.nostalgia.auth.util.exception.NostalgiaUserPasswordDoesNotExistException;
import org.nostalgia.common.util.NostalgiaRandomUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service implementation for managing user password operations.
 * <p>
 * This class provides the business logic for handling user password-related operations,
 * such as sending password reset emails and updating user passwords in the system.
 * It interacts with various ports for reading and saving user data and sending emails.
 * </p>
 */
@Service
@Transactional
@RequiredArgsConstructor
class NostalgiaUserPasswordServiceImpl implements NostalgiaUserPasswordService {

    private final NostalgiaUserReadPort userReadPort;
    private final NostalgiaUserSavePort userSavePort;
    private final NostalgiaUserMailService userMailService;
    private final PasswordEncoder passwordEncoder;


    /**
     * Handles the forgot password request by sending an email to the user with instructions to create a new password.
     * <p>
     * This method checks if the user exists based on the provided email address. If the user is found,
     * it sets or updates the user's password with a temporary value and the current time as the forgotten password timestamp.
     * An email is then sent to the user with instructions to create a new password.
     *
     * @param forgotPasswordRequest the request containing the user's email address.
     * @throws NostalgiaEmailAddressNotValidException if the email address does not correspond to any existing user.
     */
    @Override
    public void forgotPassword(final NostalgiaPasswordForgotRequest forgotPasswordRequest) {

        final String emailAddress = forgotPasswordRequest.getEmailAddress();
        final NostalgiaUser user = userReadPort.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new NostalgiaEmailAddressNotValidException(emailAddress));

        final var passwordBuilder = NostalgiaUser.Password.builder()
                .forgotAt(LocalDateTime.now());

        if (user.getPassword() != null) {
            passwordBuilder.value(user.getPassword().getValue());
        } else {
            passwordBuilder.value(NostalgiaRandomUtil.generateText(15));
        }

        user.setPassword(passwordBuilder.build());

        final NostalgiaUser savedUser = userSavePort.save(user);
        userMailService.sendPasswordCreateEmail(savedUser);
    }


    /**
     * Checks the validity of changing the user's password.
     * <p>
     * This method verifies if the password change request is valid by checking the
     * existence and expiry of the password change request associated with the given password ID.
     * It throws an exception if the password cannot be changed due to invalid conditions.
     *
     * @param passwordId The ID of the password to check for change validity.
     * @throws NostalgiaUserPasswordDoesNotExistException  if no password is found for the given ID.
     * @throws NostalgiaUserPasswordCannotChangedException if the password cannot be changed due to invalid conditions.
     */
    @Override
    public void checkPasswordChangingValidity(final String passwordId) {

        final NostalgiaUser.Password password = userReadPort.findByPasswordId(passwordId)
                .orElseThrow(() -> new NostalgiaUserPasswordDoesNotExistException(passwordId))
                .getPassword();

        this.checkChangingValidity(password);
    }


    /**
     * Creates a new password for a user identified by the given password ID.
     * <p>
     * This method updates the user's password with the new password provided in the request.
     * It first verifies if the password change request is valid based on the time elapsed since
     * the password change request was initiated. If the request is valid, it updates the password
     * and saves the changes.
     *
     * @param passwordId    The unique identifier of the user whose password is being updated.
     * @param createRequest The request object containing the new password details.
     * @throws NostalgiaUserPasswordDoesNotExistException  if no user is found with the provided password ID.
     * @throws NostalgiaUserPasswordCannotChangedException if the password change request is invalid due to
     *                                               the elapsed time or other conditions that prevent the password from being changed.
     */
    @Override
    public void createPassword(final String passwordId,
                               final NostalgiaPasswordCreateRequest createRequest) {

        final NostalgiaUser user = userReadPort.findByPasswordId(passwordId)
                .orElseThrow(() -> new NostalgiaUserPasswordDoesNotExistException(passwordId));

        this.checkChangingValidity(user.getPassword());

        NostalgiaUser.Password password = NostalgiaUser.Password.builder()
                .value(passwordEncoder.encode(createRequest.getPassword()))
                .build();
        user.setPassword(password);

        userSavePort.save(user);
    }


    /**
     * Checks the validity of changing the password.
     * <p>
     * This method verifies if the password change request is valid based on the time elapsed since the
     * password change request was initiated or the password was created. It distinguishes between
     * a newly created password and an existing one to determine if the change request is within
     * the allowable time frame. It throws an exception if the password cannot be changed.
     *
     * @param password The NostalgiaUser.Password object representing the user's password.
     * @throws NostalgiaUserPasswordCannotChangedException if the password cannot be changed due to invalid conditions.
     */
    private void checkChangingValidity(final NostalgiaUser.Password password) {

        Optional<LocalDateTime> forgotAt = Optional
                .ofNullable(password.getForgotAt());

        boolean isFirstCreation = forgotAt.isEmpty() && password.getUpdatedAt() == null;
        if (isFirstCreation) {
            this.checkExpiration(password.getId(), password.getCreatedAt());
            return;
        }

        if (forgotAt.isEmpty()) {
            throw new NostalgiaUserPasswordCannotChangedException(password.getId());
        }

        this.checkExpiration(password.getId(), forgotAt.get());
    }

    /**
     * Checks if the password change request has expired.
     * <p>
     * This method determines if the time elapsed since the provided timestamp exceeds the allowable time frame
     * for changing the password. It throws an exception if the password change request is deemed expired.
     *
     * @param id   The unique identifier of the password being validated.
     * @param date The timestamp used to check if the password change request has expired.
     * @throws NostalgiaUserPasswordCannotChangedException if the time elapsed since the timestamp exceeds the allowable time frame.
     */
    private void checkExpiration(final String id,
                                 final LocalDateTime date) {

        boolean isExpired = LocalDateTime.now()
                .minusHours(2)
                .isBefore(date);
        if (!isExpired) {
            throw new NostalgiaUserPasswordCannotChangedException(id);
        }
    }

}
