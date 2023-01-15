package br.dev.marcoalmeida.mb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                newUser("player1"),
                newUser("player2"), newUser("player3"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin((formLogin) ->
                        formLogin
                                .defaultSuccessUrl("/swagger-ui/index.html")
                )
            .csrf().disable()
            .authorizeHttpRequests(autorize -> autorize
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/i18n/**").permitAll()
                .requestMatchers("/content/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/management/health").permitAll()
                .requestMatchers("/management/health/**").permitAll()
                .requestMatchers("/management/info").permitAll()
                .anyRequest().authenticated())
            .headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails newUser(String username) {
        return User.builder()
                .username(username)
                .password(passwordEncoder().encode(username))
                .roles("USER")
                .build();
    }

}
