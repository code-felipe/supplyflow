package com.custodia.supply;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.custodia.supply.auth.handler.LoginSuccessHandler;;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	
	// El authenticationManager desde aca para ayudar al UserDetailsService	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	      .authorizeHttpRequests(auth -> auth
	          // public
	          .requestMatchers("/", "/login", "/error",
	                           "/css/**", "/js/**", "/images/**",
	                           "/list**", "/api/users/**",
	                           "/webjars/**").permitAll()
	          .requestMatchers(HttpMethod.GET,  "/register").permitAll()
	          .requestMatchers(HttpMethod.POST, "/register").permitAll()
	          // anything else requires auth.
	          .anyRequest().authenticated()
	      )

	      // Login local (My form)
	      .formLogin(login -> login
	          .loginPage("/login")
	          .usernameParameter("email")      // <input name="email">
	          .passwordParameter("password")
	          .successHandler(successHandler)  // existent handler
	          .failureUrl("/login?error")
	          .permitAll()
	      )
	      // Logout
	      .logout(logout -> logout
	          .logoutUrl("/logout")
	          .logoutSuccessUrl("/login?logout")
	          .permitAll()
	      )

	      .exceptionHandling(ex -> ex.accessDeniedPage("/error_403"));

	    return http.build();
	}

	
	
	// Reemplace el autenticationManager por este, debido a un ciclo infinito con el proxy
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
	    JdbcUserDetailsManager uds = new JdbcUserDetailsManager(dataSource);
	    uds.setUsersByUsernameQuery("""
	        SELECT email AS username, password, is_active AS enabled
	        FROM users
	        WHERE email=?
	    """);
	    uds.setAuthoritiesByUsernameQuery("""
	        SELECT u.email AS username, a.authority
	        FROM users u
	        JOIN authorities a ON u.role_id = a.id
	        WHERE u.email=?
	    """);
	    return uds;
	}

}
