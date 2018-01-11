package com.his.ts.mybatis.dao;

import com.his.ts.mybatis.bean.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */
public interface TestInterFace {
    public int testInterFace();

    public UserInfo testUser();

    public int insertUser(UserInfo userInfo);

    //新增加的方法
    List<UserInfo> selectALL();

    public List<UserInfo> selectFrom();
}
