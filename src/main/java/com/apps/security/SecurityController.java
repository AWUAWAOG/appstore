package com.apps.security;

import com.apps.domain.request.AuthRequest;
import com.apps.domain.response.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        String result = securityService.getTokenFromRequest(authRequest);
        if (!result.isBlank()) {
            logger.warn(result);
            return new ResponseEntity<>(new AuthResponse(result), HttpStatus.CREATED);
        }
        logger.warn(result);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
