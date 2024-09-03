package org.nostalgia.common.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * A custom validator implementation for the {@link EmailAddress} annotation.
 * Validates whether the provided email invalid domain
 * specified regular expression.
 */
class EmailAddressValidator implements ConstraintValidator<EmailAddress, String> {

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9çğıöşü._%+-]+[a-zA-Z0-9çğıöşü]+@[a-zA-Z0-9]+[.-]?[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";

    /**
     * Checks whether the given value is a valid email or not.
     * <p>Some valid emails are:</p>
     * <ul>
     *     <li>user@example.com</li>
     *     <li>john.doe123@example.co.uk</li>
     *     <li>admin_123@example.org</li>
     * </ul>
     *
     * <p>Some invalid emails are:</p>
     * <ul>
     *     <li>user@invalid</li>
     *     <li>user@invalid!.com</li>
     *     <li>user@.com</li>
     * </ul>
     *
     * @param email object to validate
     * @return true if the value is valid, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (!StringUtils.hasText(email)) {
            return true;
        }
        return email.matches(EMAIL_REGEX);
    }
}
