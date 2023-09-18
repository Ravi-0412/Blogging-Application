package com.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// to inject instances using '@Autowired' so using '@component'.

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	// jb koi unauthorized user access karna chahega 'authorized' api ko 
	// tb ye function call hoga.
	// error bhejenge is fn ke through sb parameter ke help se.
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");

	}

}
