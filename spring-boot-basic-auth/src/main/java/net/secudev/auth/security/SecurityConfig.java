package net.secudev.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private AuthenticationProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and()
		
		.formLogin().disable()	
		
		.httpBasic().and()
		
		.cors().disable().csrf().disable()
		
		//Alternative ci dessous à @PreAuthorize("hasRole('ROLE_admin')") dans le controller admin		
		//.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("admin").and()
		
		//Permet d'intercepter les acces refusés et passer une classe perso AccessDenyHandler ou je log les evenements
		.exceptionHandling().accessDeniedHandler(new AccessDenyHandler()).and()
		
		//desactive la gestion de session
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
