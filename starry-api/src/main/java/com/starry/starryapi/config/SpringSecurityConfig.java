package com.starry.starryapi.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("guest");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/task-job/**").hasRole("vip1");
        http.formLogin();
        //http.httpBasic();
    }
}
