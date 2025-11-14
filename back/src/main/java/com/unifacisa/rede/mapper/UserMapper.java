package com.unifacisa.rede.mapper;

import com.unifacisa.rede.dto.UserDTO;
import com.unifacisa.rede.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserDTO dto);

    List<UserDTO> toDTOList(List<UserEntity> entities);
}
