package org.nostalgia.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for role responses.
 * <p>
 * The {@link NostalgiaRolesResponse} class encapsulates the information that is sent back
 * to the client as a response for role-related operations.
 * </p>
 */
@Getter
@Setter
public class NostalgiaRolesResponse {

    private String id;
    private String name;
    private NostalgiaRoleStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
