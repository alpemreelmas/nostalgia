package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.enums.NostalgiaConfigurationParameter;
import org.nostalgia.auth.service.NostalgiaUserMailService;
import org.nostalgia.common.model.NostalgiaMail;
import org.nostalgia.common.model.enums.NostalgiaMailTemplate;
import org.nostalgia.common.service.NostalgiaMailService;
import org.nostalgia.parameter.model.NostalgiaParameter;
import org.nostalgia.parameter.port.NostalgiaParameterReadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link NostalgiaUserMailService} interface for sending user-related emails.
 * <p>
 * This service is responsible for composing and sending emails to users, specifically for scenarios
 * like password creation. It uses the {@link NostalgiaMailService} to handle the actual sending of emails
 * and interacts with the parameter read port to fetch necessary configurations.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaUserMailServiceImpl implements NostalgiaUserMailService {

    private final NostalgiaMailService mailService;
    private final NostalgiaParameterReadPort parameterReadPort;


    /**
     * Sends an email to the user to create a password.
     * <p>
     * This method composes an email with a link for the user to create their password. It fetches
     * the front-end URL from the configuration parameters and uses it to generate the complete URL
     * included in the email. The email template used is {@link NostalgiaMailTemplate#CREATE_PASSWORD}.
     * </p>
     *
     * @param user The user to whom the password creation email will be sent.
     */
    @Override
    public void sendPasswordCreateEmail(NostalgiaUser user) {

        final Map<String, Object> parameters = Map.of(
                "userFullName", user.getFullName(),
                "url", this.findFeUrl().concat("/create-password/").concat(user.getPassword().getId())
        );

        final NostalgiaMail mail = NostalgiaMail.builder()
                .to(List.of(user.getEmailAddress()))
                .template(NostalgiaMailTemplate.CREATE_PASSWORD)
                .parameters(parameters)
                .build();

        mailService.send(mail);
    }

    /**
     * Retrieves the front-end URL from configuration parameters.
     * <p>
     * This method fetches the value of the front-end URL from the parameter read port. If the parameter is not found,
     * it returns a default value defined by {@link NostalgiaConfigurationParameter#FE_URL}.
     * </p>
     *
     * @return The front-end URL as a string.
     */
    private String findFeUrl() {
        return parameterReadPort
                .findByName(NostalgiaConfigurationParameter.FE_URL.name())
                .orElse(NostalgiaParameter.from(NostalgiaConfigurationParameter.FE_URL))
                .getDefinition();
    }

}
