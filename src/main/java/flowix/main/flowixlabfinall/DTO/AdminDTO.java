package flowix.main.flowixlabfinall.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AdminDTO {
    String telegramId;
//    String hashUser;
//    String authDateUser;
}
