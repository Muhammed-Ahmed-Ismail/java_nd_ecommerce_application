package com.example.demo.controllers;

import com.example.demo.Utils.FieldInjector;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class OrderControllerTest {
    OrderController orderController = new OrderController();
    UserRepository userRepository = mock(UserRepository.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setup()
    {
        FieldInjector.injectFiled(orderController,"userRepository",userRepository);
        FieldInjector.injectFiled(orderController,"orderRepository",orderRepository);
        User user = getUser();
        given(userRepository.findByUsername(any())).willReturn(user);
    }

    @Test
    public void submitOrder()
    {
        User user = getUser();
        ResponseEntity<UserOrder> order = orderController.submit(user.getUsername());
        assertNotNull(order.getBody());
        assertEquals(user.getCart().getTotal(),order.getBody().getTotal());
    }

    private User getUser() {

        String userName = "mohamed";
        String password = "1234567";
        User user = new User();
        Cart cart = new Cart();
        IntStream.range(0,10).forEach(i->cart.addItem(getItem()));
        user.setCart(cart);
        user.setUsername(userName);
        user.setPassword(password);
        return user;
    }
    private Item getItem() {
        Item item = new Item();
        item.setName("item1");
        item.setId(1L);
        item.setPrice(new BigDecimal(1));
        return item;
    }
}
