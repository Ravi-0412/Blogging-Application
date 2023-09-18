package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// token se related jitna bhi operation perform karna hoga 
// isme rakhenge

@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60; // in mili second
	
	private String secret = "jwtTokenKey";

	// retrieving username from jwt token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token , Claims::getSubject);
	}
	
	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token , Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token , Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	// for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
			 .setSigningKey(secret)
			 .parseClaimsJws(token)
			 .getBody();
	}
	
	// check if token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims =  new HashMap<>();
		return doGenerateToken(claims , userDetails.getUsername());
	}
	
	
	// while creating token :
//	1) Define claims of the token like issuer, Expiration, Subject, and Id
// 2)Sign the JWt using the HS512 algorithm and secret key.
// 3) Acc to the JWS compact serialization 
	// compaction of the JWt to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	// validate token
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
