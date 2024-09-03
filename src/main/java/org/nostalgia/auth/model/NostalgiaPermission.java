package org.nostalgia.auth.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.nostalgia.auth.model.enums.NostalgiaPermissionCategory;
import org.nostalgia.common.model.BaseDomainModel;

/**
 * Represents a permission entity in the system.
 * This class extends {@link BaseDomainModel} to inherit common properties such as ID and auditing fields.
 * It encapsulates information about a specific permission granted within the system.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NostalgiaPermission extends BaseDomainModel {

    private String id;
    private String name;
    private NostalgiaPermissionCategory category;
    private boolean isSuper;

}
