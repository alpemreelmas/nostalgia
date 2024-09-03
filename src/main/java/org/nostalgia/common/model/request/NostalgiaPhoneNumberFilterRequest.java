package org.nostalgia.common.model.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.common.util.NostalgiaJsonUtil;

/**
 * A class representing a phone number in filtering requests, including its country code and line number.
 */
@Getter
@Setter
public class NostalgiaPhoneNumberFilterRequest {

    /**
     * The country code of the phone number
     */
    @Size(min = 1, max = 3)
    private String countryCode;

    /**
     * The line number of the phone number
     */
    @Size(min = 7, max = 15)
    private String lineNumber;

    /**
     * This method returns a JSON representation of the object for validation exception messages.
     */
    @Override
    public String toString() {
        return NostalgiaJsonUtil.toJson(this);
    }
}
