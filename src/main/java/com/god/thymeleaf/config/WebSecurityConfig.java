package com.god.thymeleaf.config;

import org.aspectj.weaver.BCException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  //test시에는 비활성 시킴
                .authorizeRequests()
                .antMatchers("/","/account/register", "/css/**", "/api/**").permitAll() //누구나 접속 가능
                .anyRequest().authenticated()
                .and()       //종료 단위

                .formLogin()// login의 경우
                .loginPage("/account/login")// login page설정
                .permitAll()//모두 접속 가능
                .and()//종료 단위

                .logout()
                .permitAll();  //모두 접속 가능
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                //테이블명과 컬럼은 customize 가능함.
                //인증
                .usersByUsernameQuery("select username,password,enabled "
                        + "from user "
                        + "where username = ?")
                //권한
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join user u on ur.user_id =u.id"
                        + " inner join role r on ur.role_id=r.id  "
                        + "where u.username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}