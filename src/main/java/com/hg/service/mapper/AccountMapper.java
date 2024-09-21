package com.hg.service.mapper;

import com.hg.domain.User;
import com.hg.service.dto.AdminUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    //    AdminUserDTO toDto(User user);
}
