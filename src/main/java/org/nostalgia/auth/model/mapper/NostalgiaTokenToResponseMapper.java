package org.nostalgia.auth.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nostalgia.auth.model.NostalgiaToken;
import org.nostalgia.auth.model.response.NostalgiaTokenResponse;
import org.nostalgia.common.model.mapper.BaseMapper;

/**
 * {@link NostalgiaTokenToResponseMapper} is an interface that defines the mapping between a {@link NostalgiaToken} and an {@link NostalgiaTokenResponse}.
 * This interface uses the MapStruct annotation @Mapper to generate an implementation of this interface at compile-time.
 * <p>The class provides a static method {@code initialize()} that returns an instance of the generated mapper implementation.
 * <p>The interface extends the MapStruct interface {@link BaseMapper}, which defines basic mapping methods.
 * The interface adds no additional mapping methods, but simply defines the types to be used in the mapping process.
 */
@Mapper
public interface NostalgiaTokenToResponseMapper extends BaseMapper<NostalgiaToken, NostalgiaTokenResponse> {

    /**
     * Initializes the mapper.
     *
     * @return the initialized mapper object.
     */
    static NostalgiaTokenToResponseMapper initialize() {
        return Mappers.getMapper(NostalgiaTokenToResponseMapper.class);
    }

}
