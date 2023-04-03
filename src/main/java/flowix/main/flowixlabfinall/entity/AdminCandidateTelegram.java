package flowix.main.flowixlabfinall.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCandidateTelegram {
    String telegramId;
    String chatId;
    String username;
}
