package com.sudin.blog.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class AuthenticationManagerProvider extends WebSecurityConfigurerAdapter {


    /* This Override method was added in order to get the token */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }


    /**
     * See: https://github.com/spring-projects/spring-boot/issues/11136
     *
     * @return
     * @throws Exception
     */

      /*  If you want to continue with boot starter packages,
     according to release notes you need to override authanticationManagerBean method
      inside the WebSecurityConfigurerAdapter
      User this link for further details
      https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#authenticationmanager-bean
      */

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
