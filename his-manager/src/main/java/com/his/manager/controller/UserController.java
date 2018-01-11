package com.his.manager.controller;

import com.his.common.rest.BaseController;
import com.his.manager.biz.UserBiz;
import com.his.manager.domain.SysUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/user")
public class UserController  extends BaseController<UserBiz,SysUser>{

    @ApiOperation(value="获取当前用户信息", notes="获取当前用户信息")
    @GetMapping("/info")
    public SysUser user(Principal principal){
        if (principal != null) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = oAuth2Authentication.getUserAuthentication();

            SysUser sysUser = (SysUser) authentication.getPrincipal();
            return sysUser;
        }

        System.out.println(this.baseBiz.getUserByUsername("admin"));
        return null;

    }
    @ApiOperation(value="获取当前用户信息", notes="获取当前用户信息")
    @GetMapping("/getByName")
    public SysUser getUser(String username){
        System.out.println(this.baseBiz.getUserByUsername("admin"));
        return null;
    }



}