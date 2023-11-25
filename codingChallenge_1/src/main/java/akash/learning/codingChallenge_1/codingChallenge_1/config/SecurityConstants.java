package akash.learning.codingChallenge_1.codingChallenge_1.config;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class SecurityConstants {
    public static final String JWT_HEADER = "Authorization";
    public static final String KEY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // public static final SecretKey key = Jwts.SIG.HS512.key().build();
}
