
package flowix.main.flowixlabfinall.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Table(name = "flowix")
@Entity
@Getter
@Setter
@ToString(exclude = {"refreshToken", "id", "username"})
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "username")
    private String username;

    @Column(name = "telegram_id")
    private String telegramId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "role")
    private String role;

    @Column(name = "chat_id")
    private String chat_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refresh_toke_id", referencedColumnName = "id")
    private RefreshToken refreshToken;

    public Admin(String username, String telegramId, Boolean active, String role, String chat_id) {
        this.username = username;
//        this.refreshToken = refreshToken;
        this.telegramId = telegramId;
        this.active = active;
        this.role = role;
        this.chat_id = chat_id;
    }

    public Admin() {
    }
}
