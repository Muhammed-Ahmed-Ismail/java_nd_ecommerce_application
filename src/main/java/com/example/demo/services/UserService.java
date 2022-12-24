package com.example.demo.services;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User createUser(CreateUserRequest userRequest)
    {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return user;
    }
}
