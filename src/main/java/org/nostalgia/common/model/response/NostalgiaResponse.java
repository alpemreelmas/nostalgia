package org.nostalgia.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * A generic response class representing an API response.
 *
 * @param <T> The type of the response object.
 */
@Getter
@Builder
public class NostalgiaResponse<T> {

    /**
     * The timestamp indicating when the response was created.
     */
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    /**
     * Indicates whether the API request was successful or not.
     */
    private Boolean isSuccess;

    /**
     * The response object.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;


    /**
     * A pre-defined success response with no content.
     */
    public static final NostalgiaResponse<Void> SUCCESS = NostalgiaResponse.<Void>builder()
            .isSuccess(true).build();

    /**
     * Creates a success response with the specified response object.
     *
     * @param <T>      The type of the response object.
     * @param response The response object.
     * @return An instance of {@link NostalgiaResponse} representing a successful response.
     */
    public static <T> NostalgiaResponse<T> successOf(final T response) {
        return NostalgiaResponse.<T>builder()
                .isSuccess(true)
                .response(response).build();
    }

}
