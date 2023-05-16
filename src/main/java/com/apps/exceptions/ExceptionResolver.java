package com.apps.exceptions;

import org.hibernate.HibernateException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class ExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArithmeticException.class)
    public String myHandler(Exception e) {
        logger.warn("ArithmeticException" + e);
        return "unsuccessfully";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFoundException(Exception e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<HttpStatus> hibernateException(Exception e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<HttpStatus> PsqlException(Exception e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<HttpStatus> methodNotAllowedException(Exception e) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}