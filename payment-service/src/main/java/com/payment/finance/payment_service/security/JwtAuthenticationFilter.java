package com.payment.finance.payment_service.security;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.lang.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UsersDetailImplementation userDetailService;

	public JwtAuthenticationFilter(JwtService jwtService, UsersDetailImplementation userDetails) {

		this.jwtService = jwtService;
		this.userDetailService = userDetails;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) // here User is a username which given in application
																	// properties
		{
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);// Why 5 becuase the above User is length 4 with space 5
		String username = jwtService.extractUserName(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			if (jwtService.isValid(token, userDetails)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
