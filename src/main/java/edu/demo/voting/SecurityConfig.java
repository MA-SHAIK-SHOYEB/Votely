package edu.demo.voting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    // Demo-only accounts. Change these before using outside a local classroom demo.
    @Bean UserDetailsService users(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
            User.withUsername("voter@example.com").password(encoder.encode("VoterDemo123!")).roles("VOTER").build(),
            User.withUsername("admin@example.com").password(encoder.encode("AdminDemo123!")).roles("ADMIN").build()
        );
    }

    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/js/**", "/about").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/vote/**", "/dashboard").hasAnyRole("VOTER", "ADMIN")
                .anyRequest().permitAll())
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard", true).permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}
