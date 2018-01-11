package com.his.manager.biz;

import com.his.common.biz.BaseBiz;
import com.his.manager.domain.SysUser;
import com.his.manager.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/1/10.
 */
@Service
public class UserBiz extends BaseBiz<SysUserMapper,SysUser> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username){
        SysUser user = new SysUser();
        user.setUsername(username);
        return mapper.selectOne(user);
    }
}
