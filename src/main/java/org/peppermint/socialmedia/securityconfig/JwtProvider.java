package org.peppermint.socialmedia.securityconfig;

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtProvider {
//    private static SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.SECRET_KEY.getBytes());
//    public static String generateToken(Authentication authentication) {
//        String jwt = Jwts.builder()
//                .setIssuer("peppermint")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + 86400000))
//                .claim("email", authentication.getName())
//                .signWith(key)
//                .compact();
//        return jwt;
//    }
//
    public static String getEmailFromJwtToken(String jwt) {
        jwt = jwt.replace(SecurityConstants.BEARER, "").trim();
//        Claims claims = Jwts.parserBuilder().setSigningKey(key)
//                .build()
//                .parseClaimsJws(jwt)
//                .getBody();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(jwt);
        return decodedJWT.getSubject();
    }
}
