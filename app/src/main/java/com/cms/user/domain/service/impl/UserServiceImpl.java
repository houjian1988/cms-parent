package com.cms.user.domain.service.impl;

import com.cms.dto.user.UserDto;
import com.cms.user.domain.repository.UserRepository;
import com.cms.user.domain.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserDto findByid(Integer id) {
        return userRepository.findByid(id);
    }

    public UserDto findByLoginName(UserDto loginDto) {
        //加密(不可逆算法,不能解密)
        String password = DigestUtils.shaHex(loginDto.getPassword());
        loginDto.setPassword(password);
        return userRepository.findByLoginName(loginDto);
    }

    @Override
    public Map findUserById(Integer id) {
        return userRepository.findUserById(id);
    }
}
