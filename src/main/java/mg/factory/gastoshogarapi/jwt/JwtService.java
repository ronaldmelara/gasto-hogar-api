package mg.factory.gastoshogarapi.jwt;

import java.security.Key;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
public class JwtService {
    private static final String SECRET_KEY = "";

    public String getToken(UserDetails user) { return getToken(new HashMap<>(), user);}

    private String getToken(HashMap<String, Object> extraClaim, UserDetails user) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime result = now.plus(40, ChronoUnit.MINUTES);

        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(result.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getallClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getallClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){ return getClaim(token, Claims::getExpiration);}

    private boolean isTokenExpired(String token){ return getExpiration(token).before(new Date());}
}
