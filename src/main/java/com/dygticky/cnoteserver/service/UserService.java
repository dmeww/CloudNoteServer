package com.dygticky.cnoteserver.service;

import com.dygticky.cnoteserver.mapper.UserMapper;
import com.dygticky.cnoteserver.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public User login(User userLogin) {
        User user = userMapper.queryByUserName(userLogin.getUsername());

        if (user != null && user.getPassword().equals(userLogin.getPassword())) {
            return user;
        }
        return null;


    }

    public User register(User userRegister) {
        try {
            userMapper.addUser(userRegister);
            return login(userRegister);
        } catch (Exception e) {
            return null;
        }
    }

    public User getLatest(int uid) {
        User latestUser = userMapper.getLatestUser(uid);
        latestUser.setPassword(null);
        return latestUser;
    }


    public void updateUserInfo(Integer uid, String field, String value) {

        if (field.equals("sex")) {
            Integer sex = Integer.valueOf(value);
            userMapper.updateUserSex(uid, field, sex);
        } else
            userMapper.updateUser(uid, field, value);
    }
}
