package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// a ) get jwt token from request
		// token header me aa rha hoga
		
		// 'Authorization' token ka key hoga.
		// with help of this key we will get the token
		// token will start from 'Bearer token...'
		String requestToken = request.getHeader("Authorization"); 
		// now we got the token starting with with 'Bearer ....'
		System.out.println(requestToken);
		
		// is token ki help se 'username' and 'actual jwt token' ko nikalenge
		String username = null;
		// to store the actual token after removing 'Bearer'
		String token = null;
		// if not empty and format is correct
		if(requestToken != null && requestToken.startsWith("Bearer"))
		{	// removing the 'Bearer' then actual token will start from index '7'.
			token = requestToken.substring(7);
			try {
			// now getting the username
			username = this.jwtTokenHelper.getUserNameFromToken(token);
			} catch(IllegalArgumentException e) 
			{
				System.out.println("unable to get jwt token");
			}
			catch (ExpiredJwtException e) {
				System.out.println("jwt token has expired");
			} catch(MalformedJwtException e) {
				System.out.println("invalid jwt ");
			}
			
		} 
		else
		{
			System.out.println("Jwt token doesn't begin with Bearer !");
		}
		
		// b) once we got the token, we will validate
		if(username!= null && SecurityContextHolder.getContext().getAuthentication()== null)
		{
			// get user detils
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				// agar sb kuch sahi chal rha h. validation sahi h
				// tb authentication karna h
				
				// 1st get the authentication object
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else
			{
				System.out.println("Invalid jwt token");
			}
			
		} else
		{
			System.out.println("username is null or context is not null");
		}
		
		// now pass the request further
		filterChain.doFilter(request, response);
		
	}

}
