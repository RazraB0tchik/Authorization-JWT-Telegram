package flowix.main.flowixlabfinall.api;

import flowix.main.flowixlabfinall.DTO.UpdateAccessTokenDTO;
import flowix.main.flowixlabfinall.filter.FilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/tools")
public class UpdateAccessTokenController {
    @Autowired
    FilterProvider filterProvider;

    @PostMapping(value = "/update_access")
    public ResponseEntity updateAccessToken(@RequestBody UpdateAccessTokenDTO updateAccessTokenDTO){
        String newAccessToken = filterProvider.createToken(updateAccessTokenDTO.getUsername(), updateAccessTokenDTO.getRole());
        HashMap map = new HashMap<>();
        map.put("accessToken", newAccessToken);
        return ResponseEntity.ok(map);
    }

}
