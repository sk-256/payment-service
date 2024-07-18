package com.payment.finance.payment_service.security;


import com.payment.finance.payment_service.entity.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import javax.crypto.*;
import java.util.*;
import java.util.function.*;

@Service
public class JwtService {

	private final String SECRET_KEY = "d024c80a765b42792cd0362e22aee350b072ebcec41d180c5e0b61388cfcc1f3";


	public String extractUserName(String token )
	{
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isValid(String token,UserDetails user) {
		 String username = extractUserName(token);
		 return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token)
	{
		return extractClaim(token,Claims::getExpiration);
	}

	public <T>  T extractClaim(String token, Function<Claims,T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}
	private Claims extractAllClaims(String token)
	{
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

	}

	public String generateToken(Users user) {
		String token = Jwts
				      .builder()
				      .subject(user.getUsername())
				      .issuedAt(new Date(System.currentTimeMillis()))
				      .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
				      .signWith(getSigninKey())
				      .compact();

		return token;
	}

	private SecretKey getSigninKey()
	{
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
