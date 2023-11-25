package akash.learning.codingChallenge_1.codingChallenge_1.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import akash.learning.codingChallenge_1.codingChallenge_1.repository.PersonRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtTokenGenerator extends OncePerRequestFilter {

    @Autowired
    private PersonRepo personRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            // System.out.println("inside JWT generator and secret key is :\n" +
            // key.toString());
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.KEY.getBytes(StandardCharsets.UTF_8));

            String jwt_token = Jwts.builder()
                    .issuer("Akash Vyas")
                    .subject("JWT Token")
                    .claim("email", auth.getName())
                    .claim("role", getRole(auth.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date().getTime()) + (1000 * 60 * 5)))
                    .signWith(key)
                    .compact();
            System.out.println("generated jwt token is : " + jwt_token);
            response.setHeader(SecurityConstants.JWT_HEADER, jwt_token);
        }
        filterChain.doFilter(request, response);
    }

    public String getRole(Collection<? extends GrantedAuthority> collection) {
        Set<String> ans = new HashSet<>();
        for (GrantedAuthority role : collection) {
            ans.add(role.getAuthority());
        }
        String returning = String.join(",", ans);
        System.out.println("retuning role : " + returning);
        return returning;

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user-login");
    }

}
