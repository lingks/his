package com.his.manager.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by sdcuike on 2017/5/5.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    

    
    @Configuration
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        }
        
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        @Autowired
        protected com.his.manager.security.UserDetailsService userDetailsService;
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
               .antMatchers("/api/swagger-ui.html");
        }
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
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
    }
}