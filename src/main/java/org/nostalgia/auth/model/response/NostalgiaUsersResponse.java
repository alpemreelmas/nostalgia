package org.nostalgia.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for user responses.
 * <p>
 * The {@link NostalgiaUsersResponse} class encapsulates the information that is sent back
 * to the client as a response for user-related operations.
 * </p>
 */
@Getter
@Setter
public class NostalgiaUsersResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private NostalgiaUserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
