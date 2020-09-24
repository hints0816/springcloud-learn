package com.hints.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import java.util.Arrays;


@Configuration
@EnableAuthorizationServer //表明这是一个认证服务器
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//    private static final String RESOURCE_ID = "account";

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


    /*clientDetailsService注入，决定clientDetails信息的处理服务。*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");

        /*//1.认证信息从数据库获取
        clients.withClientDetails(clientDetailsService);*/

        // 2.测试用，将客户端信息存储在内存中
        // 配置两个客户端，一个用于password认证模式一个用于client认证模式(oauth2一共有四种模式)
        clients.inMemory()
                .withClient("client_1")
//                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret)
                .and()
                .withClient("client_2")
//                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret)
                .accessTokenValiditySeconds(60)//5min过期
                .and()
                .withClient("client_3")
                .resourceIds("resource1")// client_id
                .secret(finalSecret)                   // client_secret
                .authorizedGrantTypes("authorization_code","refresh_token")     // 该client允许的授权类型
                .redirectUris("http://dq18-180686j.it2004.gree.com.cn:8765/oauth/callbackCode ")
                .scopes("app")
                .autoApprove(true)//是否自动授权
                .accessTokenValiditySeconds(100000); //5min过期
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
    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new MyRedisTokenStore(redisConnectionFactory)) //Token的存储方式为内存
                .authenticationManager(authenticationManager) //WebSecurity配置好的
                .userDetailsService(userServiceDetail);//读取用户的验证信息

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain(); //新建一个令牌增强链
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);

        endpoints
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .pathMapping("/oauth/confirm_access","/custom/confirm_access");
    }*/
//    4.方式4：该方式是不把token放在redis
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore) //Token的存储方式为内存
                .authenticationManager(authenticationManager) //WebSecurity配置好的
                .userDetailsService(userServiceDetail);//读取用户的验证信息
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
                .checkTokenAccess("isAuthenticated()")//验证获取token的验证信息
               ;
    }


    @Bean
    public TokenEnhancer JwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}
