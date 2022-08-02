package base.project.restapi.transformers.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

@Setter
@Getter
@ToString
public class UserPrincipalTransformModel implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String password;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
