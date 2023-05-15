package com.apps.security;

import com.apps.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public JwtFilter(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(token) && token.startsWith("Bearer ") && jwtService.isValid(token.substring(7))) {
            String login = jwtService.getLoginFromToken(token.substring(7));
            com.apps.domain.User dbUser = userRepository.findUserByUserLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(dbUser.getUserLogin())
                    .password(dbUser.getUserPassword())
                    .roles(dbUser.getRole())
                    .build();
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userAuth);
        }
        logger.warn("doFilter method done successfully" + servletRequest + servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
