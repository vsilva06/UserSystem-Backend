package cl.losguindos.UserSystemBackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private static final String[] AUTH_WHITE_LIST = {
            "**",
            "/h2-console/**"
    };

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.csrf().disable().
                authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .headers()
                .frameOptions()
                .disable()
                .and()
                .build();
    }


}
