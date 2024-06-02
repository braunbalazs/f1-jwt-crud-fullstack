package hu.practice.formula_teams.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_URLS = List.of("/signup/**", "/login/**", "/team");
    private final PathMatcher pathMatcher;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklist tokenBlacklist;

    public JwtFilter(PathMatcher pathMatcher, JwtService jwtService, UserDetailsService userDetailsService, TokenBlacklist tokenBlacklist) {
        this.pathMatcher = pathMatcher;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklist = tokenBlacklist;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
//            final String authHeader = request.getHeader("Authorization");
            String path = request.getRequestURI();
            if (PUBLIC_URLS.stream().anyMatch(url -> pathMatcher.match(url, path))) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = jwtService.extractToken(request);
            String username = jwtService.extractUsername(token);


//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                token = authHeader.substring(7);
//                username = jwtService.extractUsername(token);
//            }

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            if ( !tokenBlacklist.isBlacklisted(token) && username != null && SecurityContextHolder.getContext()
                                                      .getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                                         .setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter()
                    .write("Forbidden");
        }
    }
}
