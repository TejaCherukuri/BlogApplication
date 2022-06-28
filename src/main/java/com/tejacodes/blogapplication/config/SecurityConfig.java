package com.tejacodes.blogapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tejacodes.blogapplication.security.CustomerUserDetailsService;
import com.tejacodes.blogapplication.security.JwtAuthenticationEntryPoint;
import com.tejacodes.blogapplication.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)		// Used to enable @Pre and @Post annotations used for controlling role based access on apis.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter()
	{
		return new JwtAuthenticationFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
			.antMatchers("/api/v1/auth/**").permitAll()
			.antMatchers("/v2/api-docs/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/swagger-ui.html/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.anyRequest()
			.authenticated();
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	// This method is used to configure role based authentication using user details from the database
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(customerUserDetailsService)
			.passwordEncoder(passwordEncoder());
		
		}

	
// This method is used to configure/define roles in-memory i.e: gets deleted upon server restart/stop
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		UserDetails user = User.builder()
//								.username("teja") 
//								.password(passwordEncoder().encode("password"))
//								.roles("USER")
//								.build();
//		
//		UserDetails admin = User.builder()
//								.username("admin")
//								.password(passwordEncoder().encode("admin"))
//								.roles("ADMIN")
//								.build();
//		
//		return new InMemoryUserDetailsManager(user,admin);
//	}
	
}
