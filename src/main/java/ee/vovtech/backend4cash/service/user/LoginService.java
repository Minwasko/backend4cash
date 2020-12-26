package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.dto.LoginDto;
import ee.vovtech.backend4cash.dto.LoginResponse;
import ee.vovtech.backend4cash.dto.RegisterDto;
import ee.vovtech.backend4cash.exceptions.InvalidUserException;
import ee.vovtech.backend4cash.model.User;
import ee.vovtech.backend4cash.repository.UserRepository;
import ee.vovtech.backend4cash.security.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public void registerUser(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRole(DbRole.USER);
        userService.save(user);
    }

    public LoginResponse login(LoginDto loginDto) {
        if (loginDto.getEmail().isBlank()) throw new InvalidUserException("Email is wrong");
        if (loginDto.getPassword().isBlank()) throw new InvalidUserException("Password is wrong");

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        MyUser myUser = (MyUser) authenticate.getPrincipal(); // UserDetails
        String token = jwtTokenProvider.generateToken(myUser);
        return LoginResponse.builder()
                .email(myUser.getUsername())
                .token(token)
                .role(myUser.getDbRole())
                .build();
    }

}
