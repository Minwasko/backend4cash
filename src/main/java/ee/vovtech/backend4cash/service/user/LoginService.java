package ee.vovtech.backend4cash.service.user;

import ee.vovtech.backend4cash.dto.LoggedInUserDto;
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
import org.springframework.security.core.parameters.P;
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
        user.setCash("300");
        userService.save(user);
    }

    public LoginResponse login(LoginDto loginDto) {
        if (loginDto.getEmail().isBlank()) throw new InvalidUserException("Email is wrong");
        if (loginDto.getPassword().isBlank()) throw new InvalidUserException("Password is wrong");

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        MyUser myUser = (MyUser) authenticate.getPrincipal(); // UserDetails
        String token = jwtTokenProvider.generateToken(myUser);
        return LoginResponse.builder()
                .id(myUser.getId())
                .email(myUser.getUsername())
                .token(token)
                .role(myUser.getDbRole())
                .success(true)
                .build();
    }

    public LoggedInUserDto getUserByIdWithTokenCheck(long id, String token) {
        System.out.println(token);
        System.out.println(jwtTokenProvider.getEmailFromToken(token.substring(7)));
        User userFromToken = userService.findByEmail(jwtTokenProvider.getEmailFromToken(token.substring(7)));
        if (userFromToken != null && userFromToken.getId() == id) {
            System.out.println("YOu can get info");
            return LoggedInUserDto.builder()
                    .id(userFromToken.getId())
                    .username(userFromToken.getUsername())
                    .email(userFromToken.getEmail())
                    .cash(userFromToken.getCash())
                    .status(userFromToken.getStatus())
                    .role(userFromToken.getRole())
                    .ownedCoins(userFromToken.getOwnedCoins())
                    .build();
        } else if (userFromToken != null && userFromToken.getRole() == DbRole.ADMIN) {
            User userToGetInfo = userService.findById(id);
            return LoggedInUserDto.builder()
                    .id(userToGetInfo.getId())
                    .username(userToGetInfo.getUsername())
                    .email(userToGetInfo.getEmail())
                    .cash(userToGetInfo.getCash())
                    .status(userToGetInfo.getStatus())
                    .role(userToGetInfo.getRole())
                    .ownedCoins(userToGetInfo.getOwnedCoins())
                    .build();
        }
        throw new InvalidUserException("Trying to access another users info");
    }

}
