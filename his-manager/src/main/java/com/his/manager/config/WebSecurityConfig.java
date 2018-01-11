package com.his.manager.config;

import com.his.manager.security.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1)
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            //.formLogin().and() // 基于Form表单的认证，用户可自定义
            .httpBasic(); // 启用HTTPBasic认证

        /**
         * 8号晚
         */
        http
                .httpBasic().realmName("practice");
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .requestMatchers().antMatchers("/oauth/authorize")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/authorize").authenticated()
                .anyRequest().authenticated();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());// 可指定从数据库加载用户信息
        /*auth.inMemoryAuthentication()
            .withUser("user")
            .password("user")
            .authorities("READ")
            .and()
            .withUser("admin")
            .password("admin")
            .authorities("READ", "WRITE");*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/ui",
                "/swagger-resources/configuration/ui",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v2/api-docs",
                "/api/ui",
                "/api/swagger-resources/configuration/ui",
                "/api/configuration/ui",
                "/api/swagger-resources/**",
                "/api/configuration/security",
                "/api/swagger-ui.html",
                "/api/webjars/**");
    }
}
