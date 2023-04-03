package flowix.main.flowixlabfinall.api;

import flowix.main.flowixlabfinall.DTO.AdminDTO;
import flowix.main.flowixlabfinall.entity.Admin;
import flowix.main.flowixlabfinall.filter.FilterProvider;
import flowix.main.flowixlabfinall.refresh_token.RefreshTokenUtil;
import flowix.main.flowixlabfinall.repositories.AdminRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping( "/auth")
public class AdminLoginController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    FilterProvider filterProvider;

    @Autowired
    RefreshTokenUtil refreshTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Value("${telegram.bot.token}")
    String telegramToken;

    @PostMapping("/loginAdmin")
    public ResponseEntity<Object> loginUser(@RequestBody AdminDTO adminDTO) throws NoSuchAlgorithmException, InvalidKeyException {
//        System.out.println("im here");
//        System.out.println(adminDTO.getTelegramId() + adminDTO.getHashUser() + adminDTO.getAuthDateUser());
//        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKeySpec = new SecretKeySpec(telegramToken.getBytes(StandardCharsets.UTF_16), "HmacSHA256");
//        mac.init(secretKeySpec);
//        byte[] userDate = messageDigest.digest(adminDTO.getAuthDateUser().getBytes(StandardCharsets.UTF_16));
//
//        byte[] preFinalHash = mac.doFinal(userDate);
//
//        String hash = Hex.encodeHexString(preFinalHash);
//
//        if(hash.equals(adminDTO.getHashUser())){
//            System.out.println("ok");
//        }
//        else{
//            System.out.println(hash);
//            System.out.println(adminDTO.getHashUser());
//        }

        Admin admin = adminRepository.findAdminByTelegramId(adminDTO.getTelegramId());
        if(admin == null){
            return ResponseEntity.status(403).body("Admin not found!");
        }
        else {
            refreshTokenUtil.updateRefreshToken(admin);
            String accessToken = filterProvider.createToken(admin.getUsername(), admin.getRole());
            Map<Object, Object> responseElem = new HashMap<>();
            responseElem.put("usernameAuth", admin.getUsername());
            responseElem.put("tokenAdmin", accessToken);
            responseElem.put("roleAdmin", admin.getRole());
            return ResponseEntity.ok(responseElem);
        }
    }

    @GetMapping(value = "/admin")
    public ResponseEntity getMain(){

        return ResponseEntity.ok("hi");
    }

}
