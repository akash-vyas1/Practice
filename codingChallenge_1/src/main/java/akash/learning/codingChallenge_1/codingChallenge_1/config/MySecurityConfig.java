package akash.learning.codingChallenge_1.codingChallenge_1.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class MySecurityConfig {
    @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http,
    // MvcRequestMatcher.Builder mvc)

    // public SecurityFilterChain securityFilterChain(HttpSecurity http,
    // HandlerMappingIntrospector introspector)

    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions().disable());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAfter(new JwtTokenGenerator(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtValidationFilter(), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/add-new-user", "/contact").permitAll()
                .requestMatchers("/get-user/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .requestMatchers("/super-admin").hasRole("SUPER_ADMIN")
                .requestMatchers("/user-login").authenticated()
                .requestMatchers("/validate").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated());

        http.formLogin(formLogin -> formLogin.permitAll());
        http.httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    // return new MvcRequestMatcher.Builder(introspector);
    // }

}
