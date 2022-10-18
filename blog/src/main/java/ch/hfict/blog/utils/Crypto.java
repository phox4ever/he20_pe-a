package ch.hfict.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Crypto {

    public static String secret;
    private final static String issuer = "hf-ict";
    public static String hash(String string) {
        return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
    }

    public static String createToken(Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withPayload(getPayload(userId))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
                return verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            return null;
        }
    }

    private static Map<String, Object> getPayload(Long userId) {
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("userId", userId);
        return payloadClaims;
    }



}
