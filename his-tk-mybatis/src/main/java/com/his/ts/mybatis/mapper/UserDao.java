package com.his.ts.mybatis.mapper;

import com.his.mybatis.base.BaseMapper;
import com.his.ts.mybatis.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<UserInfo> {

    public List<UserInfo> selectFrom();
}