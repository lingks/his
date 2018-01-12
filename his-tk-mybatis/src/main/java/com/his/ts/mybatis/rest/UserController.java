package com.his.ts.mybatis.rest;

import com.his.ts.mybatis.bean.UserInfo;
import com.his.ts.mybatis.dao.TestInterFace;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
//@Log4j //注解在类上，为类提供一个属性名为log的日志对象
@Log //注解在类上，为类提供一个属性名为log的日志对象
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private TestInterFace testInterFace;

    @RequestMapping("/get")
    @ResponseBody
    UserInfo getUser() {
        return testInterFace.testUser();
    }

    @RequestMapping("/add")
    @ResponseBody String add() {
        UserInfo user = new UserInfo();
        user.setUsername("username123中国");
        user.setPassword("password123中国");
        testInterFace.insertUser(user);
        log.info("插入成功");
        return "插入成功";
    }

    //新增的接口方法
    @RequestMapping("/getall")
    @ResponseBody
    List<UserInfo> getall() {
        return testInterFace.selectALL();
    }
    //新增的接口方法
    @RequestMapping("/selectFrom")
    @ResponseBody
    List<UserInfo> selectFrom() {
        return testInterFace.selectFrom();
    }


}