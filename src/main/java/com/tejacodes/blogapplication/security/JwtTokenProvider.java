package com.tejacodes.blogapplication.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tejacodes.blogapplication.exception.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	/*
	 * Method to generate JWT Token
	 */
	public String generateToken(Authentication authentication)
	{
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
		
		String token =  Jwts.builder()
							.setSubject(username)
							.setIssuedAt(new Date(System.currentTimeMillis()))
							.setExpiration(expireDate)
							.signWith(SignatureAlgorithm.HS512, jwtSecret)
							.compact();
		return token;
	}
	
	/*
	 * Extract Username from the token
	 */
	public String getUsernameFromToken(String token)
	{
		Claims claims = Jwts.parser()
							.setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody();
		
		return claims.getSubject();
	}
	
	/*
	 * Validate JWT Token
	 */
	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}
		catch(SignatureException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
		}
		catch(MalformedJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		}
		catch(ExpiredJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT Toke Expired");
		}
		catch(UnsupportedJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
		}
		catch(IllegalArgumentException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT Claims String is empty");
		}
	}
}
