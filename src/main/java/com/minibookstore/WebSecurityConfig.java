package com.minibookstore;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth)throws Exception{
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username,password,enabled from user where username=?")
			.authoritiesByUsernameQuery("select username,role from role where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/book/**").permitAll()
			.anyRequest().permitAll()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.rememberMe().tokenValiditySeconds(2419200)
			.and()
			.logout().permitAll()
			.and()
			.logout()
			.logoutSuccessUrl("/");
		http.exceptionHandling().accessDeniedPage("/403");
		
	}
}