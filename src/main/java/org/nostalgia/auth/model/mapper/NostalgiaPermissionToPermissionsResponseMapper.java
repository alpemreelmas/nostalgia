package org.nostalgia.auth.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nostalgia.auth.model.NostalgiaPermission;
import org.nostalgia.auth.model.response.NostalgiaPermissionsResponse;
import org.nostalgia.common.model.mapper.BaseMapper;

/**
 * {@link NostalgiaPermissionToPermissionsResponseMapper} is an interface that defines the mapping between an {@link NostalgiaPermission} and an {@link NostalgiaPermissionsResponse}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface NostalgiaPermissionToPermissionsResponseMapper extends BaseMapper<NostalgiaPermission, NostalgiaPermissionsResponse> {

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static NostalgiaPermissionToPermissionsResponseMapper initialize() {
        return Mappers.getMapper(NostalgiaPermissionToPermissionsResponseMapper.class);
    }

}
