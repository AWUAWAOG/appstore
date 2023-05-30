package com.apps.security;

import com.apps.domain.User;
import com.apps.domain.request.AuthRequest;
import com.apps.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String getTokenFromRequest(AuthRequest authRequest) {
        Optional<User> user = userRepository.findUserByUserLogin(authRequest.getLogin());
        if (user.isPresent() && passwordEncoder.matches(authRequest.getPassword(), user.get().getUserPassword())) {
            logger.info(user.toString());
            return jwtService.createJwtToken(authRequest.getLogin());
        }
        logger.warn(user.toString());
        return "";
    }
}