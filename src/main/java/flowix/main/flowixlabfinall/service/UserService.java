package flowix.main.flowixlabfinall.service;



import flowix.main.flowixlabfinall.entity.Admin;
import flowix.main.flowixlabfinall.entity.AdminCandidateTelegram;
import flowix.main.flowixlabfinall.entity.RefreshToken;
import flowix.main.flowixlabfinall.entity.UserDetailElementAd;
import flowix.main.flowixlabfinall.exceptions.AdminRegisteredException;
import flowix.main.flowixlabfinall.refresh_token.RefreshTokenUtil;
import flowix.main.flowixlabfinall.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserDetailElementAd userDetailElementAd;

    @Autowired
    RefreshTokenUtil refreshTokenUtil;

    private Collection<GrantedAuthority> authorities;

    public UserService(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Admin admin = adminRepository.findAdminByUsername(username);
        authorities.add(new SimpleGrantedAuthority(admin.getRole()));
        return new UserDetailElementAd(admin.getUsername(), "", authorities, admin.getActive());
    }

    public Admin loadUserByTelegramId(String telegramId) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByTelegramId(telegramId);
        if(admin == null){
            throw new UsernameNotFoundException("User with telegram_id - " + telegramId + " not found!");
        }
        else{
            return admin;
        }
    }

    public void saveAdmin(AdminCandidateTelegram adminCandidateTelegram) throws AdminRegisteredException {
        String telegramId = adminCandidateTelegram.getTelegramId();
        String username = adminCandidateTelegram.getUsername();
        String chatId = adminCandidateTelegram.getChatId();
        if(adminRepository.findAdminByTelegramId(telegramId) == null){
            Admin newAdmin = new Admin(username, telegramId, true, "ADMIN", chatId);
            RefreshToken refreshToken = refreshTokenUtil.createRefreshToken();
            newAdmin.setRefreshToken(refreshToken);
            adminRepository.save(newAdmin);
        }
        else{
            throw new AdminRegisteredException("Admin with " + telegramId + " is already in the database");
        }
    }

}
