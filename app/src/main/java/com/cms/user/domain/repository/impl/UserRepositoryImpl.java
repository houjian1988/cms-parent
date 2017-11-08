package com.cms.user.domain.repository.impl;

import com.cms.dto.user.UserDto;
import com.cms.user.domain.repository.UserRepository;
import com.cms.user.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserMapper userMapper;

    public UserDto findByid(Integer id) {
        return userMapper.findByid(id);
    }

    public UserDto findByLoginName(UserDto loginDto) {
        return userMapper.findByLoginName(loginDto);
    }

    @Override
    public Map findUserById(Integer id) {
        return userMapper.findUserById(id);
    }
}
