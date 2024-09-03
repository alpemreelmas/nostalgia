package org.nostalgia.auth.port.impl;

import lombok.RequiredArgsConstructor;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.model.entity.NostalgiaPermissionEntity;
import org.nostalgia.auth.model.mapper.NostalgiaPermissionEntityToDomainMapper;
import org.nostalgia.auth.port.NostalgiaPermissionReadPort;
import org.nostalgia.auth.repository.NostalgiaPermissionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Adapter class implementing the {@link NostalgiaPermissionReadPort} interface.
 * Retrieves {@link NostalgiaPermission} from the repository and maps them to domain models.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class NostalgiaPermissionAdapter implements NostalgiaPermissionReadPort {

    private final NostalgiaPermissionRepository permissionRepository;


    private final NostalgiaPermissionEntityToDomainMapper permissionEntityToDomainMapper = NostalgiaPermissionEntityToDomainMapper.initialize();


    /**
     * Retrieves all {@link NostalgiaPermission} from the repository.
     *
     * @return A list of all {@link NostalgiaPermission}.
     */
    @Override
    public List<NostalgiaPermission> findAll() {
        final List<NostalgiaPermissionEntity> permissionEntities = permissionRepository.findAll();
        return permissionEntityToDomainMapper.map(permissionEntities);
    }

    /**
     * Retrieves all {@link NostalgiaPermission} where {@code isSuper} flag is false from the repository.
     *
     * @return A list of {@link NostalgiaPermission} where {@code isSuper} is false.
     */
    @Override
    public List<NostalgiaPermission> findAllByIsSuperFalse() {
        List<NostalgiaPermissionEntity> permissionEntities = permissionRepository.findAllByIsSuperFalse();
        return permissionEntityToDomainMapper.map(permissionEntities);
    }

    /**
     * Retrieves {@link NostalgiaPermission} with IDs present in the given set from the repository.
     *
     * @param ids The set of permission IDs to retrieve.
     * @return A list of {@link NostalgiaPermission} matching the provided IDs.
     */
    @Override
    public List<NostalgiaPermission> findAllByIds(final Set<String> ids) {
        List<NostalgiaPermissionEntity> permissionEntities = permissionRepository.findAllById(ids);
        return permissionEntityToDomainMapper.map(permissionEntities);
    }

}
