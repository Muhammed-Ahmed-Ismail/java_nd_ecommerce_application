package com.example.demo.services;

import com.example.demo.controllers.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException{
    private static final Logger log = LogManager.getLogger(UserController.class);

    public NotFound() {
    }

    public NotFound(String message) {
        super(message);
        log.error(message);
    }
}
