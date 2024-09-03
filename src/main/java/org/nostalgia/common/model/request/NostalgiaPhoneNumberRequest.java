package org.nostalgia.common.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.nostalgia.common.util.NostalgiaJsonUtil;

/**
 * A class representing a phone number in requests, including its country code and line number.
 */
@Getter
@Setter
public class NostalgiaPhoneNumberRequest {

    /**
     * The country code of the phone number
     */
    @NotBlank
    private String countryCode;

    /**
     * The line number of the phone number
     */
    @NotBlank
    private String lineNumber;

    /**
     * This method returns a JSON representation of the object for validation exception messages.
     */
    @Override
    public String toString() {
        return NostalgiaJsonUtil.toJson(this);
    }

    public boolean isBlank() {
        return StringUtils.isBlank(this.countryCode) && StringUtils.isBlank(this.lineNumber);
    }

}
