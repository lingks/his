package com.his.manager.repository;

import com.his.manager.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {

    @Query("SELECT u FROM SysUser u WHERE LOWER(u.username) = LOWER(:username)")
    SysUser findByUsernameCaseInsensitive(@Param("username") String username);

    @Query
    SysUser findByEmail(String email);

    @Query
    SysUser findByEmailAndActivationKey(String email, String activationKey);

    @Query
    SysUser findByEmailAndResetPasswordKey(String email, String resetPasswordKey);


}