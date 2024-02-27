package mg.factory.gastoshogarapi.config;

import lombok.RequiredArgsConstructor;
import mg.factory.gastoshogarapi.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
}
