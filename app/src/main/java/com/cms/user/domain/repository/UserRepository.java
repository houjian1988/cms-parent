package com.cms.user.domain.repository;

import com.cms.dto.user.UserDto;

public interface UserRepository {
    /**
     * @param id 用户ID
     * @return 用户信息
     */
    UserDto findByid(Integer id);

    /**
     * 根据登录名与密码登录用户
     *
     * @param loginDto 输入登录条件:登录名与密码
     * @return User 对象
     */
    UserDto findByLoginName(UserDto loginDto);
}
