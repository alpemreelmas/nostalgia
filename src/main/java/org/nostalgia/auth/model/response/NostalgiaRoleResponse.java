package org.nostalgia.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaPermissionCategory;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NostalgiaRoleResponse {

    private String id;
    private String name;
    private NostalgiaRoleStatus status;
    private List<Permission> permissions;
    private String createdUser;
    private LocalDateTime createdAt;
    private String updatedUser;
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    public static class Permission {
        private String id;
        private String name;
        private NostalgiaPermissionCategory category;
    }

}
