package ee.vovtech.backend4cash.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MyUser extends User {

    private final Long id;
    private final DbRole dbRole;


    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, DbRole dbRole) {
        super(username, password, authorities);
        this.id = id;
        this.dbRole = dbRole;
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                  Long id, DbRole dbRole) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.dbRole = dbRole;
    }
}
