package com.fundooproject.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenUtil {

	static String tokenKey = "tzABx31ddQLAWEP";

	public String createToken(long id) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			return JWT.create().withClaim("id", id).sign(algorithm);
		} catch (JWTCreationException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public Long decodeToken(String token) {
		Verification verification = JWT.require(Algorithm.HMAC256(tokenKey));
		JWTVerifier jwtverifier = verification.build();
		DecodedJWT decodedjwt = jwtverifier.verify(token);
		Claim claim = decodedjwt.getClaim("id");
		return claim.asLong();
	}
}