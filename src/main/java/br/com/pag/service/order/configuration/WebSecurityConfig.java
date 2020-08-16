package br.com.pag.service.order.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .antMatchers("/actuator/*").permitAll()
            .anyRequest().authenticated();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .passwordEncoder(passwordEncoder)
                .withUser("john")
                .password(passwordEncoder.encode("123"))
                .roles("client_read", "product_read", "order_read")
            .and()
                .withUser("tom")
                .password(passwordEncoder.encode("111"))
                .roles("client_read", "product_read", "order_read", "product_write", "order_write")
            .and()
                .withUser("fred")
                .password(passwordEncoder.encode("222"))
                .roles("client_read", "product_read", "order_read", "client_write")
            .and()
                .withUser("admin")
                .password(passwordEncoder.encode("789"))
                .roles("client_read", "product_read", "order_read", "client_write", "product_write", "order_write", "order_delete");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
