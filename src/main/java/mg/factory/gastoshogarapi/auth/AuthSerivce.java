package mg.factory.gastoshogarapi.auth;

import lombok.RequiredArgsConstructor;
import mg.factory.gastoshogarapi.entities.User;
import mg.factory.gastoshogarapi.jwt.JwtService;
import mg.factory.gastoshogarapi.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthSerivce {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login (LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        Integer userid = null;
        Object obj = user;
        if(obj instanceof User){
            userid= ((User)obj).getId();
        }
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .id(userid)
                .build();
    }



}
