package com.kevin.cloud.service.config;
import com.kevin.cloud.service.service.UserDetailsServiceImpl;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @program: kevin-cloud-dubbo2.0
 * @description:
 * @author: kevin
 * @create: 2020-01-05 16:20
 **/
@Configuration
@EnableWebSecurity
@EnableResourceServer  // 该注解是将 oauth2 也作为认证服务器
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 配置默认的加密方式
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean()  {
        return new UserDetailsServiceImpl();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //开放user/login 供给前端登录
        web.ignoring()
                .antMatchers("/oauth/login", "**/front/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }

    /**
     * 用于支持 password 模式
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 将授权访问配置改为注解方式
         * @see LoginController#info()
         */
        /*http.exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

        http.exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 授权访问
                .antMatchers("/user/info").hasAuthority("USER")
                .antMatchers("/user/logout").hasAuthority("USER");
        ;
    }
}