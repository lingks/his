package com.his.manager.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Administrator on 2017/11/1.
 */
public interface CustomGrantedAuthority extends GrantedAuthority {
    String getAuthorityId();
}
