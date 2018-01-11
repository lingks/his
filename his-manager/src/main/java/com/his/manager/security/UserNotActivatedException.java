package com.his.manager.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by lings on 2017/10/26.
 */
public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
