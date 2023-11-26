package akash.learning.codingChallenge_1.codingChallenge_1.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.naming.NameNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import akash.learning.codingChallenge_1.codingChallenge_1.model.Role;
import akash.learning.codingChallenge_1.codingChallenge_1.repository.RoleRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

public class JwtValidationFilter extends OncePerRequestFilter {

    // @Autowired
    private RoleRepo roleRepo;

    public JwtValidationFilter(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Claims claims = null;
        String token = request.getHeader(SecurityConstants.JWT_HEADER);
        if (token != null) {
            SecretKey key = Keys
                    .hmacShaKeyFor(SecurityConstants.KEY
                            .getBytes(StandardCharsets.UTF_8));
            try {
                System.out.println("Verifying JWT token");
                claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = (String) claims.get("email");
                String rolesFromJwt = (String) claims.get("role");
                System.out.println("roles from Jwt : " + rolesFromJwt);
                String[] roles = rolesFromJwt.split(",");
                Set<Role> authorities = new HashSet<>();
                String roleLog = "";
                for (String role : roles) {
                    Optional<Role> fetchedRole = roleRepo.findByName(role);
                    if (fetchedRole.isPresent()) {
                        roleLog += fetchedRole.get().getAuthority();
                        authorities.add(fetchedRole.get());
                    } else {
                        throw new NameNotFoundException("role not found !");
                    }
                }
                System.out.println("Role : from jwt validation filter : " + roleLog);
                response.setHeader(SecurityConstants.JWT_HEADER, token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                throw new BadCredentialsException("Jwt token is expired");
            } catch (UnsupportedJwtException e) {
                throw new UnsupportedJwtException("unsupported jwt exception");
            } catch (MalformedJwtException e) {
                throw new MalformedJwtException("Jwt token is altered !");
            } catch (SignatureException e) {
                throw new SignatureException("Signature is not valid");
            } catch (SecurityException e) {
                throw new SecurityException("Security Exception");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Illegal args");
            } catch (NameNotFoundException e) {
                try {
                    throw new NameNotFoundException("Role is not available !");
                } catch (NameNotFoundException e1) {
                    System.out.println("Role not found in database");
                }
            }
        } else {
            System.out.println("No JWT token in header");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> paths = new ArrayList<>();
        paths.add("/contact");
        paths.add("/user-login");
        paths.add("/add-new-user");
        paths.add("/h2");
        paths.add("/login");
        String path = request.getServletPath();
        if (path.contains("/h2")) {
            path = "/h2";
        }
        if (paths.contains(path)) {
            return true;
        } else {
            return false;
        }
    }

}
