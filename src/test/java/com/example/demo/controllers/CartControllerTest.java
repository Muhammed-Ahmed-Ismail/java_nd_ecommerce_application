package com.example.demo.controllers;

import com.example.demo.Utils.FieldInjector;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    CartController cartController = new CartController();
    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);
    ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        FieldInjector.injectFiled(cartController, "userRepository", userRepository);
        FieldInjector.injectFiled(cartController, "cartRepository", cartRepository);
        FieldInjector.injectFiled(cartController, "itemRepository", itemRepository);

    }

    @Test
    public void addToCart() {
        User user = getUser();
        Item item = getItem();
        given(userRepository.findByUsername(any())).willReturn(user);
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("mohamed");
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(10);
        ResponseEntity<Cart> cart = cartController.addTocart(cartRequest);
        assertNotNull(cart);
        assertEquals(new BigDecimal(10), cart.getBody().getTotal());
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(getItem());
        }
        assertEquals(itemList, cart.getBody().getItems());
    }
    @Test
    public void removeFromCart()
    {
        User user = getUser();
        user.setCart(getFullCart());
        Item item = getItem();
        given(userRepository.findByUsername(any())).willReturn(user);
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("mohamed");
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(5);
        ResponseEntity<Cart> cart = cartController.removeFromcart(cartRequest);
        assertEquals(new BigDecimal(5),cart.getBody().getTotal());
    }
    private User getUser() {

        String userName = "mohamed";
        String password = "1234567";
        User user = new User();
        Cart cart = new Cart();
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
    private Cart getFullCart()
    {
        Cart cart = new Cart();
        IntStream.range(1,11).forEach((i)-> cart.addItem(getItem()));
        return cart;
    }
}
