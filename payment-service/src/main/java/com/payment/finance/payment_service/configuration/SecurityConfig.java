package com.payment.finance.payment_service.configuration;

import com.payment.finance.payment_service.security.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	private final UsersDetailImplementation userDetailsServiceImp;

	private final JwtAuthenticationFilter authenticationFilter;



	public SecurityConfig(UsersDetailImplementation userDetailsServiceImp,
			JwtAuthenticationFilter authenticationFilter) {
		super();
		this.userDetailsServiceImp = userDetailsServiceImp;
		this.authenticationFilter = authenticationFilter;
	}



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
				                 req-> req.requestMatchers("/login/**","/register/**","/swagger-ui/**","/payments/create/**","/payments/status/**","/payments/process/**","/payments/refund/**")
				                 .permitAll()
				                 .anyRequest()
				                 .authenticated()

						).userDetailsService(userDetailsServiceImp)
				         .sessionManagement(session -> session
				        		                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				         .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				         .build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		 return configuration.getAuthenticationManager();
	}

}
