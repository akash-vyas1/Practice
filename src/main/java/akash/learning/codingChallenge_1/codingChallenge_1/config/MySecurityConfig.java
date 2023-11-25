package akash.learning.codingChallenge_1.codingChallenge_1.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.addFilterAfter(new JwtTokenGenerator(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtValidationFilter(), BasicAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .antMatchers("/h2-console/**", "/contact", "/add-new-user").permitAll()
                .antMatchers("/validate").hasAnyRole("ADMIN", "USER", "SUPER_ADMIN")
                .antMatchers("/admin", "super-admin").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/get-user/**").hasRole("SUPER_ADMIN")
                .antMatchers("/user-login").authenticated()
                .anyRequest().authenticated();
        http.formLogin();
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // @Bean
    // // public SecurityFilterChain securityFilterChain(HttpSecurity http,
    // // MvcRequestMatcher.Builder mvc)

    // public SecurityFilterChain securityFilterChain(HttpSecurity http,
    // HandlerMappingIntrospector introspector)

    // // public SecurityFilterChain securityFilterChain(HttpSecurity http)
    // throws Exception {
    // http.csrf(csrf -> csrf.disable());
    // http.headers(headers -> headers.frameOptions().disable());

    // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // http.addFilterAfter(new JwtTokenGenerator(),
    // BasicAuthenticationFilter.class);
    // http.addFilterBefore(new JwtValidationFilter(),
    // BasicAuthenticationFilter.class);

    // http.authorizeHttpRequests(request -> request
    // // .requestMatchers("/myAccount").hasAuthority("view_account")
    // // .requestMatchers("/myBalance").hasAuthority("view_balance")
    // // .requestMatchers("/myCards").hasAuthority("view_cards")
    // // .requestMatchers("/myLoans").hasAuthority("view_loans")
    // // .requestMatchers("/register").hasAuthority("add_user")
    // // .requestMatchers("/register", "/user-login").authenticated()
    // // .requestMatchers(mvc.pattern("/get-user/**"),
    // // mvc.pattern("/add-new-user")).permitAll()
    // // .requestMatchers(mvc.pattern("/get-user/*")).permitAll()
    // // .requestMatchers(mvc.pattern("/h2-console/*")).permitAll()
    // // .requestMatchers(new MvcRequestMatcher(introspector,
    // // "/h2-console/**")).permitAll()

    // .requestMatchers("/add-new-user", "/contact").permitAll()
    // // .requestMatchers("/get-user/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
    // // .requestMatchers("/super-admin").hasRole("SUPER_ADMIN")
    // // .requestMatchers("/user-login").authenticated()
    // // .requestMatchers("/validate").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
    // .anyRequest().authenticated()
    // // .requestMatchers(new AntPathRequestMatcher("/h2-console")).permitAll()
    // // .requestMatchers("/notices", "/contact/contact-detail").permitAll()
    // // .requestMatchers("/getAuthorities/**").hasAuthority("view_authorities")
    // // .anyRequest().authenticated()
    // );

    // http.formLogin(formLogin -> formLogin.permitAll());
    // http.httpBasic(withDefaults());

    // return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    // return new MvcRequestMatcher.Builder(introspector);
    // }

}
