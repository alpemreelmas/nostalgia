package org.nostalgia.common.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A Domain model representing a phone number, including its country code and line number.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class NostalgiaPhoneNumber {

    /**
     * The country code of the phone number
     */
    private String countryCode;

    /**
     * The line number of the phone number
     */
    private String lineNumber;

}
