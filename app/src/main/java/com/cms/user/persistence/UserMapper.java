package com.cms.user.persistence;

import com.cms.dto.user.UserDto;

import java.util.Map;

public interface UserMapper {

    UserDto findByid(Integer id);

    UserDto findByLoginName(UserDto loginDto);

    Map findUserById(Integer id);
}
