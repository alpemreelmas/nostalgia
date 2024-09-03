package org.nostalgia.auth.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.nostalgia.auth.model.enums.NostalgiaPermissionCategory;
import org.nostalgia.common.model.entity.BaseEntity;


/**
 * Represents permissions stored in the "AYS_PERMISSION" table.
 *
 * <p>
 * This class extends the BaseEntity class and includes fields for the unique identifier
 * of the permission, its name, and its category.
 * </p>
 */
@Entity
@Getter
@Setter
@Table(name = "NOSTALGIA_PERMISSION")
public class NostalgiaPermissionEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private NostalgiaPermissionCategory category;

    @Column(name = "IS_SUPER")
    private boolean isSuper;

}
