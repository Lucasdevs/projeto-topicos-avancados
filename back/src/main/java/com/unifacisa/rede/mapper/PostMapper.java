package com.unifacisa.rede.mapper;

import com.unifacisa.rede.dto.PostDTO;
import com.unifacisa.rede.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostEntity toEntity(PostDTO dto);

    PostDTO toDto(PostEntity entity);

    List<PostDTO> toDTOList(List<PostEntity> entities);

}
