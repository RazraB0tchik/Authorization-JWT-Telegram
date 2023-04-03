package flowix.main.flowixlabfinall.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "refresh_tokens")
@Component
@Getter
@Setter
@ToString(exclude = "admin")
public class RefreshToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "token")
    private String token;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name="expired_date")
    private Date expiredDate;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.ALL)
    private Admin admin;
    public RefreshToken(String token, Date startDate, Date expiredDate) {
        this.token = token;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
    }

    public RefreshToken() {
    }
}
