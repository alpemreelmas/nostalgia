package org.nostalgia.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaPermissionCategory;

@Getter
@Setter
public class NostalgiaPermissionsResponse {

    private String id;
    private String name;
    private NostalgiaPermissionCategory category;

}
