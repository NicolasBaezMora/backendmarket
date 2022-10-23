package com.ezab.backendMarket.security;

import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.exceptions.NoDataFoundException;
import com.ezab.backendMarket.helpers.TokenService;
import com.ezab.backendMarket.repositories.UserRepository;
import com.ezab.backendMarket.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) {
                String userName = tokenService.getUsernameFromToken(jwt);

                User user = userRepository.findByUsername(userName)
                        .orElseThrow(() -> new NoDataFoundException("Username " + userName + " doesn't exist"));


                UserPrincipal principal = UserPrincipal.create(user);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                principal.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // Inyectamos el usuario en spring security
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        filterChain.doFilter(request, response);

    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7, bearerToken.length());
        return null;
    }

}
