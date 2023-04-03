package flowix.main.flowixlabfinall.refresh_token;

import flowix.main.flowixlabfinall.entity.Admin;
import flowix.main.flowixlabfinall.entity.RefreshToken;
import flowix.main.flowixlabfinall.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RefreshTokenUtil {

    @Value("${jwt.token.refresh.expired}")
    private long timeRange;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(){
        String token = UUID.randomUUID().toString();
        Date startDate  = new Date();
        Date finishDate = new Date(startDate.getTime() + timeRange);
        RefreshToken refreshToken = new RefreshToken(token, startDate, finishDate);
        return refreshToken;
    }

    public void updateRefreshToken(Admin admin){
        RefreshToken refreshToken = admin.getRefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        Date startDate  = new Date();
        refreshToken.setStartDate(startDate);
        refreshToken.setExpiredDate(new Date(startDate.getTime() + timeRange));
        refreshTokenRepository.save(refreshToken);
    }

}
