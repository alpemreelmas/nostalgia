package org.nostalgia.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaIdentity;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.port.NostalgiaPermissionReadPort;
import org.nostalgia.auth.service.NostalgiaPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing permissions.
 * Retrieves permissions based on the user's identity.
 */
@Service
@RequiredArgsConstructor
class NostalgiaPermissionServiceImpl implements NostalgiaPermissionService {

    private final NostalgiaPermissionReadPort permissionReadPort;

    private final NostalgiaIdentity identity;


    /**
     * Retrieves all permissions based on the user's identity.
     * If the user is a super admin, all permissions are fetched.
     * Otherwise, only non-super permissions are retrieved.
     *
     * @return A list of {@link NostalgiaPermission} objects based on the user's identity.
     */
    @Override
    public List<NostalgiaPermission> findAll() {

        if (identity.isSuperAdmin()) {
            return permissionReadPort.findAll();
        }

        return permissionReadPort.findAllByIsSuperFalse();
    }

}
