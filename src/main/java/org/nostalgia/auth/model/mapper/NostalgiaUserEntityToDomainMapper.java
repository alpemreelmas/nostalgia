package org.nostalgia.auth.model.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.nostalgia.auth.model.NostalgiaUser;
import org.nostalgia.auth.model.entity.NostalgiaUserEntity;
import org.nostalgia.common.model.mapper.BaseMapper;

/**
 * {@link NostalgiaUserEntityToDomainMapper} is an interface that defines the mapping between an {@link NostalgiaUserEntity} and an {@link NostalgiaUser}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface NostalgiaUserEntityToDomainMapper extends BaseMapper<NostalgiaUserEntity, NostalgiaUser> {

    @Override
    NostalgiaUser map(NostalgiaUserEntity userEntity);

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static NostalgiaUserEntityToDomainMapper initialize() {
        return Mappers.getMapper(NostalgiaUserEntityToDomainMapper.class);
    }

}
