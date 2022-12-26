package com.example.demo.controllers;

import com.example.demo.Utils.FieldInjector;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    UserController userController = new UserController();
    UserService userService = new UserService();
    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Before
    public void setup() {
        FieldInjector.injectFiled(userService, "cartRepository", cartRepository);
        FieldInjector.injectFiled(userService, "userRepository", userRepository);
        FieldInjector.injectFiled(userService, "passwordEncoder", passwordEncoder);
        FieldInjector.injectFiled(userController, "userService", userService);

    }

    @Test
    public void createUserSanity() {
        when(passwordEncoder.encode("12345678")).thenReturn("12345678H");
        CreateUserRequest userRequest = new CreateUserRequest();
        String userName = "mohamed";
        String password = "12345678";
        String confirmPassword = "12345678";
        userRequest.setUsername(userName);
        userRequest.setPassword(password);
        userRequest.setConfirmPassword(confirmPassword);
        ResponseEntity<User> createdUser = userController.createUser(userRequest);
        assertNotNull(createdUser.getBody());
        assertEquals(userName, createdUser.getBody().getUsername());
        assertEquals("12345678H", createdUser.getBody().getPassword());
    }

    @Test
    public void createUserWithoutEqualPasswordAndConfirmPassword() {
        CreateUserRequest userRequest = new CreateUserRequest();
        String userName = "mohamed";
        String password = "1234567";
        String confirmPassword = "11111";
        userRequest.setUsername(userName);
        userRequest.setPassword(password);
        userRequest.setConfirmPassword(confirmPassword);
        ResponseEntity<User> createdUser = userController.createUser(userRequest);
        assertEquals(HttpStatus.valueOf(400), createdUser.getStatusCode());
    }



    private User getUser() {

        String userName = "mohamed";
        String password = "1234567";
        String confirmPassword = "1234567";
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);

        return user;
    }
}
