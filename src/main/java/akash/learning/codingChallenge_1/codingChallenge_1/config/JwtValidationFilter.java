package akash.learning.codingChallenge_1.codingChallenge_1.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends OncePerRequestFilter {

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
                String role = (String) claims.get("role");
                response.setHeader(SecurityConstants.JWT_HEADER, token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(role));

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
        paths.add("/h2-console");
        String path = request.getServletPath();
        if (path.contains("h2-console")) {
            path = "h2-console";
        }
        if (paths.contains(path)) {
            return true;
        } else {
            return false;
        }
    }

}
