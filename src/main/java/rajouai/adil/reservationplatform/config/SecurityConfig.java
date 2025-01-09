package rajouai.adil.reservationplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import rajouai.adil.reservationplatform.auth.AuthSessionFilter;
import rajouai.adil.reservationplatform.common.Constants;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    AuthSessionFilter authSessionFilter;

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // This was here so that the frontend could easily access the
                // backend during development.
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    String origin = request.getHeader("Origin");
                    if (origin != null) {
                        configuration.setAllowedOrigins(List.of(origin));
                    }
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                // Always create a session
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.ALWAYS))
                // Authorize requests
                .authorizeHttpRequests(authorize -> authorize
                        // Permit access to static resources, frontend, and auth
                        .requestMatchers(Constants.AUTH_WHITELIST)
                        .permitAll()
                        // Require authentication for all other requests
                        .anyRequest()
                        .authenticated())
                // Add the session filter before the authentication filter
                .addFilterBefore(authSessionFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}