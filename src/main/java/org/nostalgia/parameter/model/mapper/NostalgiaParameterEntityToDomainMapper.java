package org.nostalgia.parameter.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nostalgia.common.model.mapper.BaseMapper;
import org.nostalgia.parameter.model.NostalgiaParameter;
import org.nostalgia.parameter.model.entity.NostalgiaParameterEntity;

/**
 * {@link NostalgiaParameterEntityToDomainMapper} is an interface that defines the mapping between an {@link NostalgiaParameterEntity} and an {@link NostalgiaParameter}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface NostalgiaParameterEntityToDomainMapper extends BaseMapper<NostalgiaParameterEntity, NostalgiaParameter> {

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static NostalgiaParameterEntityToDomainMapper initialize() {
        return Mappers.getMapper(NostalgiaParameterEntityToDomainMapper.class);
    }

}
