package com.cms.user.persistence;

import com.cms.dto.user.UserDto;

public interface UserMapper {

    UserDto findByid(Integer id);

    UserDto findByLoginName(UserDto loginDto);
}
