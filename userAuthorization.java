import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class userAuthorization{
    
    public static void main(String[] args){
        Instant now = Instant.now();
        String password  = "hellow";
        byte[] secret = Base64.getDecoder().decode("eyJzdWIiOiJzZWNyZXQifQ");

        String jwts = Jwts.builder()
                      .setSubject(password)
                      .setIssuedAt(Date.from(now))
                      .setExpirationDate(Date.from(now.plus(1,ChronoUnit.MINUTES)))
                      .signWithKeys(Keys.hmacShaKeyFor(secret))
                      .compact();
    }
}