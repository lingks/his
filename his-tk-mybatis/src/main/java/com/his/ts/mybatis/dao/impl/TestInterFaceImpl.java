package com.his.ts.mybatis.dao.impl;


import com.his.ts.mybatis.bean.UserInfo;
import com.his.ts.mybatis.dao.TestInterFace;
import com.his.ts.mybatis.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestInterFaceImpl implements TestInterFace {
    @Autowired
    UserDao userDao;
    @Override public int testInterFace() {
        return 0;
    }

    @Override public UserInfo testUser() {
        return new UserInfo();
    }

    @Override public int insertUser(UserInfo userInfo) {
        return userDao.insert(userInfo);
    }

    //新增加的实现
    @Override
    public List<UserInfo> selectALL(){
        return userDao.selectAll();
    }


    @Override
    public List<UserInfo> selectFrom(){
        return userDao.selectFrom();
    }
}