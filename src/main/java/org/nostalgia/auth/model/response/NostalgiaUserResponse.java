package org.nostalgia.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.common.model.NostalgiaPhoneNumber;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NostalgiaUserResponse {

    private String id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private NostalgiaPhoneNumber phoneNumber;
    private String city;
    private NostalgiaUserStatus status;
    private List<Role> roles;
    private String createdUser;
    private LocalDateTime createdAt;
    private String updatedUser;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    public static class Role {
        private String id;
        private String name;
    }
}
