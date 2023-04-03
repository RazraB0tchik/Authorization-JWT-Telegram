package flowix.main.flowixlabfinall.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UpdateAccessTokenDTO {
    String username;
    String role;
}
