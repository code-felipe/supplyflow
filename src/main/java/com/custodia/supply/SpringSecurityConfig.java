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

//	@Bean
//	public UserDetailsService userDetailsService() throws Exception {
//
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//		manager.createUser(
//				User.withUsername("user").password(this.passwordEncoder.encode("user")).roles("USER").build());
//
//		manager.createUser(User.withUsername("admin").password(this.passwordEncoder.encode("admin"))
//				.roles("ADMIN", "USER").build());
//
//		return manager;
//	}

//	@Bean
//    public AuthenticationSuccessHandler loginSuccessHandler(IUserService userService) {
//        return (request, response, auth) -> {
//            String email = auth.getName();
//            Long userId = null;
//            try {
//                userId = userService.findByEmail(email).getId();
//            } catch (Exception ignored) {}
//
//            response.sendRedirect(
//                (userId != null) ? "/user/view/" + userId : "/user/list"
//            );
//        };
//    }
	// For later
	// , AuthenticationSuccessHandler loginSuccessHandler
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// public assets + root + login
				.requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers(HttpMethod.GET,  "/register").permitAll()
		        .requestMatchers(HttpMethod.POST, "/register").permitAll()
				//==== Replaced by Anotations ===
				// ADMIN-only primero (más estricto)
//			      .requestMatchers("/user/form/**", "/user/delete/**").hasRole("ADMIN")
//			      .requestMatchers("/user/toggle/**").hasRole("ADMIN")
//			      .requestMatchers("/request/delete/**").hasRole("ADMIN")
//			      .requestMatchers("/supply_item/form/**").hasRole("ADMIN")

				// USER o ADMIN (más permisivo)
//			      .requestMatchers("/user/view/**", "/user/list").hasAnyRole("USER","ADMIN")
//			      .requestMatchers("/request/form/**").hasAnyRole("USER","ADMIN")
//			      .requestMatchers("/supply_item/list/**").hasAnyRole("USER","ADMIN")
				//== Other Implementation ==
				  // ADMIN-only (ejemplos típicos de escritura)
//	              .requestMatchers("/user/form/**", "/user/delete/**", "/user/toggle/**").hasRole("ADMIN")
//	              .requestMatchers("/request/delete/**", "/supply_item/form/**").hasRole("ADMIN")

	              // Vistas para USER/ADMIN/SUPERVISOR
//	              .requestMatchers("/user/view/**").hasAnyRole("ADMIN","SUPERVISOR","CONCIERGE")
//	              .requestMatchers("/user/list/**").hasAnyRole("ADMIN","SUPERVISOR")
//	              .requestMatchers("/supply_item/list/**", "/request/form/**").hasAnyRole("ADMIN","SUPERVISOR", "CONCIERGE")

	              // Concierge: restringe a SU vista (/user/view/{suId}) con chequeo adicional abajo o @PreAuthorize
	              // Si su vista tiene un patrón general, mantener autenticado y validar en el controlador.

				// everything else requires auth
				.anyRequest().authenticated())
				// This implementation for latter
				.formLogin(login -> login
						.loginPage("/login")
						.usernameParameter("email")      // <input name="email">
					    .passwordParameter("password") 
						.successHandler(successHandler)
					    .failureUrl("/login?error")   // ⟵ ESTA// Needs correction is not working -> LoginSuccessHandler
						
						
//					.defaultSuccessUrl("/user/list", false) //is now part of successHandler
						// .successHandler(loginSuccessHandler) // <<<< aquí
						.permitAll())
				.logout(logout -> logout.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
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


//	@Bean
//	AuthenticationManager authManager(HttpSecurity http) throws Exception {
//		  return http.getSharedObject(AuthenticationManagerBuilder.class)
//			        .jdbcAuthentication()
//			        .dataSource(dataSource)
//			        .passwordEncoder(passwordEncoder)
//			        // Autenticar por email
//			        .usersByUsernameQuery("""
//			            SELECT email AS username, password, is_active AS enabled
//			            FROM users
//			            WHERE email=?
//			        """)
//			        // Autoridad desde la FK role_id -> authorities.id
//			        .authoritiesByUsernameQuery("""
//			            SELECT u.email AS username, a.authority
//			            FROM users u
//			            JOIN authorities a ON u.role_id = a.id
//			            WHERE u.email=?
//			        """)
//			        .and()
//			        .build();
////		return http.getSharedObject(AuthenticationManagerBuilder.class).jdbcAuthentication().dataSource(dataSource)
////				.passwordEncoder(passwordEncoder)
////				.usersByUsernameQuery("select username, password, enabled from users where email=?")
////				.authoritiesByUsernameQuery(
////						"select u.email, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.email=?")
////				.and().build();
//	}

}
