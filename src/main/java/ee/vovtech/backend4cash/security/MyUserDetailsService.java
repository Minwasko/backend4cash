package ee.vovtech.backend4cash.security;

import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service @AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // find the user in db -> if not there throw exception else return
        // We are using email in place of username, since the username in our case doesnt have to be exclusive, but email does
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(format("email not found for: %s", email));
        return new MyUser(user.getEmail(), user.getPassword(), getAuthorities(user), user.getId(), user.getRole());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return getRoles(user)
                .map(DbRole::toSpringRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Stream<DbRole> getRoles(User user) {
        if (user.getRole().isAdmin()) {
            return Arrays.stream(DbRole.values());
        }
        return Stream.of(user.getRole());
    }
}
