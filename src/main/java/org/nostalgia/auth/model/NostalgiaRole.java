package org.nostalgia.auth.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.common.model.BaseDomainModel;

import java.util.List;

/**
 * Represents a role entity in the system.
 * This class extends {@link BaseDomainModel} to inherit common properties such as ID and auditing fields.
 * It encapsulates information about a specific role granted within the system.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NostalgiaRole extends BaseDomainModel {

    private String id;
    private String name;
    private NostalgiaRoleStatus status;

    private List<NostalgiaPermission> permissions;

    /**
     * Checks if the role's status is active.
     *
     * @return {@code true} if the role's status is {@link NostalgiaRoleStatus#ACTIVE}, otherwise {@code false}.
     */
    public boolean isActive() {
        return NostalgiaRoleStatus.ACTIVE.equals(this.status);
    }

    /**
     * Checks if the role's status is passive.
     *
     * @return {@code true} if the role's status is {@link NostalgiaRoleStatus#PASSIVE}, otherwise {@code false}.
     */
    public boolean isPassive() {
        return NostalgiaRoleStatus.PASSIVE.equals(this.status);
    }

    /**
     * Activates the role by setting its status to {@link NostalgiaRoleStatus#ACTIVE}.
     * This method should be called when the role needs to be marked as active in the system.
     */
    public void activate() {
        this.setStatus(NostalgiaRoleStatus.ACTIVE);
    }

    /**
     * Passivates the role by setting its status to {@link NostalgiaRoleStatus#PASSIVE}.
     * This method should be called when the role needs to be marked passive in the system.
     */
    public void passivate() {
        this.setStatus(NostalgiaRoleStatus.PASSIVE);
    }

    /**
     * Checks if the role's status is deleted.
     *
     * @return {@code true} if the role's status is {@link NostalgiaRoleStatus#DELETED}, otherwise {@code false}.
     */
    public boolean isDeleted() {
        return NostalgiaRoleStatus.DELETED.equals(this.status);
    }


    /**
     * Sets the role's status to deleted ({@link NostalgiaRoleStatus#DELETED}), marking the role as inactive.
     * This method effectively removes the role from active use within the system.
     */
    public void delete() {
        this.status = NostalgiaRoleStatus.DELETED;
    }

}
