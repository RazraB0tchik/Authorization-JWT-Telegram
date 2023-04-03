package flowix.main.flowixlabfinall.filter;

import flowix.main.flowixlabfinall.entity.Admin;
import flowix.main.flowixlabfinall.exceptions.InvalidRefreshToken;
import flowix.main.flowixlabfinall.exceptions.TokenException;
import flowix.main.flowixlabfinall.repositories.AdminRepository;
import flowix.main.flowixlabfinall.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class FilterProvider{

    @Autowired
    UserService userService;

    @Value(value = "${jwt.token.secret}")
    private String secretKey;

    @Value(value = "${jwt.token.expired}")
    private long expiredDate;

    @Autowired
    AdminRepository adminRepository;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); //при инициализации секретный ключ будет закодирован
    }

    public String createToken(String username, String role){

        Date now = new Date();
        Date expiredNewDate = new Date(now.getTime() + expiredDate);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredNewDate)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolve(HttpServletRequest request) throws TokenException {
        String bearerToken = request.getHeader("Authorization");
        if((bearerToken != null) && (bearerToken.startsWith("Bearer_"))){
            return bearerToken.substring(7, bearerToken.length());
        }
            return null;
    }

    public boolean checkValidationToken(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        Date expiredDate = claims.getBody().getExpiration();
        if(expiredDate.before(new Date())){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkRefToken(String token) throws InvalidRefreshToken {
        String username = getSubject(token);
        Admin admin = adminRepository.findAdminByUsername(username);
        if(admin.getRefreshToken().getExpiredDate().before(new Date())){
            System.out.println("!!!!!");
            throw new InvalidRefreshToken("Refresh token spoiled");
        }
        else{
            return true;
        }
    }

    public String getSubject(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    public Authentication authenticateToken(String token){
        UserDetails userDetailElementAd = userService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetailElementAd, "", userDetailElementAd.getAuthorities());
    }
}