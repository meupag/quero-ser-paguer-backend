package br.pag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Classe que intercepta e manipula a segurança das requisições REST.
 *
 * @author brunner.klueger
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//autorizar requisições
                .anyRequest()//qualquer uma
                .authenticated()//precisam ser autenticadas
                .and()//e
                .httpBasic()//o tipo de autenticação é BASIC
                .and()//e
                .csrf().disable();//desabilita cross site request forgery

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("teste")
                .password("{noop}teste")
                .roles("USER");
    }
}
