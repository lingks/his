package com.his.manager.security;

import com.his.common.vo.RichUserDetails;
import com.his.manager.domain.SysAuthority;
import com.his.manager.domain.SysUser;
import com.his.manager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by linsheng on 17/11/5.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {



    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        SysUser user = userRepository.findByUsernameCaseInsensitive(login);

        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + login + " was not activated");
        }
        System.out.println("================================用户权限=========================================");

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Set<SysAuthority> authorities = new HashSet<>();
        //超级管理员


        for (SysAuthority authority : authorities) {
            System.out.println("权限名称：" + authority.getName());
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            CustomGrantedAuthority customGrantedAuthority = new CustomGrantedAuthority() {
                @Override
                public String getAuthorityId() {
                    return authority.getId();
                }

                @Override
                public String getAuthority() {
                    return authority.getName();
                }
            };
            grantedAuthorities.add(grantedAuthority);

            authorityList.add(customGrantedAuthority);
        }

        return  new RichUserDetails(user.getId(),user.getUsername(),user.getUsername(),user.getPassword(),user.getType(), grantedAuthorities,user.getEmployeeId());

    }


}
