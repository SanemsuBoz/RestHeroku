package com.works.utils;

import com.works.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final UserService service;
    public SecurityConfig(UserService service) {
        this.service = service;
    }

    // sql query
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder( service.encoder() );
    }

    // role -> mapping
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/note/**").hasRole("note")
                .antMatchers("/product/**").hasRole("product")
                .and()
                .formLogin().disable().csrf().disable();

    }

}
