package com.tejacodes.blogapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)		// Used to enable @Pre and @Post annotations used for controlling role based access on apis.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails user = User.builder()
								.username("teja") 
								.password(passwordEncoder().encode("password"))
								.roles("USER")
								.build();
		
		UserDetails admin = User.builder()
								.username("admin")
								.password(passwordEncoder().encode("admin"))
								.roles("ADMIN")
								.build();
		
		return new InMemoryUserDetailsManager(user,admin);
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
