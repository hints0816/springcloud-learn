package com.hints.authserver.config;

import com.hints.authserver.customImpl.ClientDetailsServiceImpl;
import com.hints.authserver.customImpl.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
@EnableAuthorizationServer //表明这是一个认证服务器
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//    private static final String RESOURCE_ID = "account";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserServiceDetail userServiceDetail;

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private @Qualifier("test2DataSource")DataSource dataSource;

    /*clientDetailsService注入，决定clientDetails信息的处理服务。*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //1.认证信息从数据库获取
        ClientDetailsServiceImpl clientDetailsService =new ClientDetailsServiceImpl();
        clientDetailsService.setRedisTemplate(redisTemplate);
        clients.withClientDetails(clientDetailsService);
    }

//    1.将token存放在redis
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        //这里应该是选择token的存储方式(这里我选择使用redis来存储token)
//        endpoints.tokenStore(new MyRedisTokenStore(redisConnectionFactory))
//                .authenticationManager(authenticationManager)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//    }

//    2.将token存放在关系型数据库
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//                .tokenStore(new MyRedisTokenStore(redisConnectionFactory)) //Token的存储方式为内存
//                .authenticationManager(authenticationManager) //WebSecurity配置好的
//                .userDetailsService(userServiceDetail);//读取用户的验证信息
//    }

    //    3.将token存放在关系型数据库,并且配置了使用jwt来生成token,即生成json web token
   /* @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new MyRedisTokenStore(redisConnectionFactory)) //Token的存储方式为内存
                .authenticationManager(authenticationManager) //WebSecurity配置好的
                .userDetailsService(userServiceDetail);//读取用户的验证信息

        *//*TokenEnhancerChain enhancerChain = new TokenEnhancerChain(); //新建一个令牌增强链
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
*//*
        endpoints
             *//*   .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)*//*
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                ;
    }*/
//    4.方式4：该方式是不把token放在redis
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore) //Token的存储方式为内存
                .authenticationManager(authenticationManager) //WebSecurity配置好的
                .userDetailsService(userServiceDetail)//读取用户的验证信息
                .authorizationCodeServices(authorizationCodeServices());

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain(); //新建一个令牌增强链(payload)
        enhancerChain.setTokenEnhancers(Arrays.asList(JwtTokenEnhancer(),jwtTokenConfig.jwtAccessTokenConverter()));

        endpoints
                .accessTokenConverter(jwtTokenConfig.jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .pathMapping("/oauth/confirm_access","/custom/confirm_access");
    }

    //目前来说是固定格式
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")//对获取token的请求不在拦截
                .checkTokenAccess("isAuthenticated()");//验证获取token的验证信息
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        JdbcAuthorizationCodeServices authorizationCodeServices = new CustomJdbcAuthorizationCodeServices(dataSource);
        return authorizationCodeServices;
    }

    public TokenEnhancer JwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}
