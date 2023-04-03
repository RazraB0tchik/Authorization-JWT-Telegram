package flowix.main.flowixlabfinall.entity;

import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDetailElementAd implements UserDetails {

    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities; //отображает разрешения данные пользователю
    private Boolean active;

    public UserDetailElementAd(String username, String password, Collection<GrantedAuthority> authorities, Boolean active) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.active = active;
    }

    public UserDetailElementAd() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
