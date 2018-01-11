package com.his.manager.config;

import com.alibaba.fastjson.JSONObject;
import com.his.cache.redis.RedisTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RestController
public class OAuth2Configuration {

    public static int token_time = 5*24*60*60;


    private final DataSource dataSource;

    public OAuth2Configuration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean// 声明TokenStore实现
    public JdbcTokenStore tokenStore() {
        JdbcTokenStore tokenStore =  new JdbcTokenStore(dataSource);
        return tokenStore;
    }

    @Bean // 声明 ClientDetails实现
    @Primary
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private final TokenStore tokenStore;


        public ResourceServerConfiguration(TokenStore tokenStore) {

            this.tokenStore = tokenStore;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .and()
                    .csrf()
                    .disable()
                    .headers()
                    .frameOptions().disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/register").permitAll()
                    .antMatchers("/api/profile-info").permitAll()
                    .antMatchers("/**").authenticated();
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("res_apptem").tokenStore(tokenStore);
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private final AuthenticationManager authenticationManager;

        private final TokenStore tokenStore;

        private final DataSource dataSource;


        @Resource
        private RedisTemplate redisTemplate;


        public AuthorizationServerConfiguration(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                                TokenStore tokenStore, DataSource dataSource) {

            this.authenticationManager = authenticationManager;
            this.tokenStore = tokenStore;
            this.dataSource = dataSource;
        }

        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        @Bean
        public ApprovalStore approvalStore() {
            return new JdbcApprovalStore(dataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // 配置TokenServices参数

            endpoints
                    .authorizationCodeServices(authorizationCodeServices())
                    .tokenEnhancer(new CustomTokenEnhancer())
                    .approvalStore(approvalStore())
                    .tokenStore(tokenStore)
                    .tokenServices(customTokenService())
                    .authenticationManager(authenticationManager);


        }

        @Bean
        @Primary
        public AuthorizationServerTokenServices customTokenService() {

            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setReuseRefreshToken(true);
            tokenServices.setSupportRefreshToken(true);
            //这句代码拦截不到access_token
            tokenServices.setTokenEnhancer(new CustomTokenEnhancer());
            tokenServices.setAccessTokenValiditySeconds(token_time);
            tokenServices.setRefreshTokenValiditySeconds(7200);
            tokenServices.setTokenStore(tokenStore);
            return tokenServices;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            clients.jdbc(dataSource);
        }

        @Bean
        protected JwtAccessTokenConverter jwtTokenEnhancer() {
            // mySecretKey为产生秘钥文件jwt.jks时所使用的密码
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
            return converter;
        }


        class CustomTokenEnhancer implements TokenEnhancer {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                final Map<String, Object> additionalInfo = new HashMap<>();
                UserDetails user = (UserDetails) authentication.getUserAuthentication().getPrincipal();
                additionalInfo.put("username", user.getUsername());
                additionalInfo.put("authorities", user.getAuthorities());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

                System.out.println(user);
                redisTemplate.setex(accessToken.toString(), token_time, JSONObject.toJSONString(user));
                System.out.println("access_token:" + accessToken);
                return accessToken;
            }
        }





    }
}
