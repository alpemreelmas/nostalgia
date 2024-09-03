package org.nostalgia.parameter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaConfigurationParameter;

import java.util.Set;

/**
 * A class representing a parameter used in the AYS application.
 */
@Getter
@Setter
@Builder
public class NostalgiaParameter {

    private String name;
    private String definition;

    /**
     * Returns the definition of a specified configuration parameter by searching through a set of parameters.
     *
     * @param configurationParameter the configuration parameter whose definition is being sought
     * @param parameters             the set of parameters to search through
     * @return the definition of the specified configuration parameter, or null if not found
     */
    public static String getDefinition(final NostalgiaConfigurationParameter configurationParameter, final Set<NostalgiaParameter> parameters) {
        return parameters.stream()
                .filter(parameter -> parameter.getName().equals(configurationParameter.name()))
                .findFirst()
                .map(NostalgiaParameter::getDefinition)
                .orElse(null);
    }

    public static NostalgiaParameter from(final NostalgiaConfigurationParameter configurationParameter) {
        return NostalgiaParameter.builder()
                .name(configurationParameter.name())
                .definition(configurationParameter.getDefaultValue())
                .build();
    }

}
