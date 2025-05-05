package com.skidata.codingtest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.skidata.codingtest.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;

	Instant now = Instant.now();
	Duration validity = Duration.ofHours(2);

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
				.withIssuer("java-poc-api")
				.withSubject(user.getUsername())
				.withExpiresAt(Date.from(now.plus(validity)))
				.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
				.withIssuer("java-poc-api")
				.build()
				.verify(token)
				.getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}
}
