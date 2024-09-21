package com.hg.service.mapper;

import com.hg.domain.User;
import com.hg.service.dto.AdminUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "id", source = "id")
    AdminUserDTO toDto(User user);
}
