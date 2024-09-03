package org.nostalgia.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nostalgia.auth.model.NostalgiaRoleFilter;
import org.nostalgia.common.model.request.NostalgiaPagingRequest;

import java.util.Set;

/**
 * Data transfer object for handling role listing requests with pagination and filtering capabilities.
 * <p>
 * The {@code NostalgiaRoleListRequest} class extends {@link NostalgiaPagingRequest} to provide additional
 * functionalities for role listing requests, including validation of sorting properties and the
 * inclusion of a {@link NostalgiaRoleFilter} for filtering roles based on specific criteria.
 * </p>
 *
 * @see NostalgiaPagingRequest
 * @see NostalgiaRoleFilter
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NostalgiaRoleListRequest extends NostalgiaPagingRequest {

    @Valid
    private NostalgiaRoleFilter filter;

    /**
     * Validates sorting properties to ensure no unsupported sorting property is used in the request.
     * <p>
     * This method overrides {@link NostalgiaPagingRequest#isOrderPropertyAccepted()} to enforce that only
     * certain sorting properties, such as "createdAt", are accepted for sorting roles.
     * </p>
     *
     * @return {@code true} if the sorting property is accepted, {@code false} otherwise.
     */
    @JsonIgnore
    @AssertTrue
    @Override
    public boolean isOrderPropertyAccepted() {
        final Set<String> acceptedFilterFields = Set.of("createdAt");
        return this.isPropertyAccepted(acceptedFilterFields);
    }

}
