package cn.varfunc.restaurant.filter;

import cn.varfunc.restaurant.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    private List<String> unprotectedEndpoints = List.of("/auth/login");

    @Value("${cn.varfunc.restaurant.jwt.token-header}")
    private String tokenHeader;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (unprotectedEndpoints.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Request method: {}", request.getMethod());

        if (request.getRequestURI().startsWith("/stores") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().startsWith("/file")) {
            filterChain.doFilter(request, response);
            return;
        }


        String token = request.getHeader(this.tokenHeader);
        if (Objects.nonNull(token) && !token.isEmpty()) {
            log.debug("token: {}", token);

            String username = JwtUtils.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (JwtUtils.validateToken(token, userDetails)) {
                log.debug("token is valid");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        List.of(new SimpleGrantedAuthority("USER")));
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Security context: {}", SecurityContextHolder.getContext());

                filterChain.doFilter(request, response);
            }
        }
    }
}