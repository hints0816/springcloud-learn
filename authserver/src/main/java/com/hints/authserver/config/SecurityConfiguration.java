package com.hints.authserver.config;


import com.hints.authserver.handler.AppLoginFailureHandler;
import com.hints.authserver.handler.SecAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity//不需要
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserServiceDetail userServiceDetail;

    @Autowired
    private SecAuthenticationSuccessHandler secAuthenticationSuccessHandler;

    @Autowired
    private AppLoginFailureHandler appLoginFailureHandler;

    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    LoginAuthenticationProvider loginAuthenticationProvider;

   /* @Autowired
    CusPreAuthenticatedAuthenticationProvider cusPreAuthenticatedAuthenticationProvider;*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder())
        /*.and()
        .authenticationProvider(authenticationProvider())*/;
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider(){
        AuthenticationProvider authenticationProvider = new CusPreAuthenticatedAuthenticationProvider();
        return authenticationProvider;
    }*/

    //密码编码器
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/icon/**","/img/**","/js/**","/favicon.ico");
    }

    //安全拦截机制,什么时候关闭csrf
    //这里定义了oauth2拦截哪些url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
            .antMatchers("/oauth/**","/users/current","/oauth2/**").permitAll()
            .anyRequest()
            .authenticated();

        /*http.exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);*/
        http.formLogin()
                .loginPage("/oauth/index") //所有拦截下来的URL都会到这个地址，默认为/login，登出的默认url为logout
                .loginProcessingUrl("/oauth/login")
                .failureHandler(appLoginFailureHandler)
                .successHandler(secAuthenticationSuccessHandler)
                .usernameParameter("session[email_or_mobile_number]")
                .passwordParameter("session[password]")
        .permitAll()
        .and()
        .addFilterAt(lindAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests();

        http.logout();

        /*
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/index").permitAll().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                })*/
                /*CustomizeUsernamePasswordAuthenticationFilter customizeUsernamePasswordAuthenticationFilter = new CustomizeUsernamePasswordAuthenticationFilter();

                AuthenticationManager authenticationManager = http
                        .getSharedObject(AuthenticationManager.class);

                customizeUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
                customizeUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(secAuthenticationSuccessHandler);
                customizeUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(appLoginFailureHandler);

        LoginAuthenticationProvider loginAuthenticationProvider = new LoginAuthenticationProvider(userServiceDetail);
        http.authenticationProvider(loginAuthenticationProvider)
                .addFilterAfter(customizeUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);*/

//        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);

//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();  //拦截所有请求 通过httpBasic进行认证
    }

    @Bean
    CustomizeUsernamePasswordAuthenticationFilter lindAuthenticationFilter() {
        CustomizeUsernamePasswordAuthenticationFilter customizeUsernamePasswordAuthenticationFilter = new CustomizeUsernamePasswordAuthenticationFilter();
        ProviderManager providerManager =
                new ProviderManager(Collections.singletonList(loginAuthenticationProvider));
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationManager(providerManager);
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(secAuthenticationSuccessHandler);
        customizeUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(appLoginFailureHandler);
        return customizeUsernamePasswordAuthenticationFilter;
    }
}