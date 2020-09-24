package com.hints.authserver.config;


import com.hints.authserver.handler.AppLoginFailureHandler;
import com.hints.authserver.handler.SecAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity//不需要
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserServiceDetail userServiceDetail;

    @Autowired
    private SecAuthenticationSuccessHandler secAuthenticationSuccessHandler;

    @Autowired
    private AppLoginFailureHandler appLoginFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder());
    }

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

    //安全拦截机制,什么时候关闭csrf
    //这里定义了oauth2拦截哪些url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**").permitAll();
        http.requestMatchers()
                .antMatchers("/oauth/login", "/oauth/index","/oauth/authorize","/users/current","/oauth/login1")
                .and()
            .authorizeRequests()
                .antMatchers("/oauth/index","/oauth/login","/users/current").permitAll()
//                .antMatchers("/oauth/token").permitAll()
//                .antMatchers("/oauth/login").permitAll()
//                .antMatchers("/oauth/check_token").permitAll()
//                .antMatchers("/oauth/authorize").permitAll()
//                .antMatchers("/oauth/confirm_access").permitAll()
//                .antMatchers("/oauth/error").permitAll()
//                .antMatchers("/oauth/approvale/confirm").permitAll()
//                .antMatchers("/oauth/approvale/error").permitAll()
                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage("/oauth/index")
                .loginProcessingUrl("/oauth/login")
//                .successHandler(secAuthenticationSuccessHandler)
                .failureHandler(appLoginFailureHandler)
                .successHandler(secAuthenticationSuccessHandler)
                .usernameParameter("session[email_or_mobile_number]")
                .passwordParameter("session[password]");
//                .permitAll();

//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();  //拦截所有请求 通过httpBasic进行认证


        //能自定义退出
//        http.authorizeRequests()
//                .antMatchers("/r/r1").hasAuthority("USER")
//                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
//                .anyRequest().permitAll()//除了/r/**，其他的请求可以访问
//                .and()//分隔
//                .formLogin()//允许表单登录
//                .successForwardUrl("/login-success");//自定义登录成功的页面地址
    }
}