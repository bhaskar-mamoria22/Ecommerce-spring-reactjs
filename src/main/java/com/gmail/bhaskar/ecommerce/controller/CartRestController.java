package com.gmail.bhaskar.ecommerce.controller;

import com.gmail.bhaskar.ecommerce.domain.Perfume;
import com.gmail.bhaskar.ecommerce.domain.User;
import com.gmail.bhaskar.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rest")
public class CartRestController {
    /**
     * Service object for working with user shopping cart.
     */
    private final UserService userService;


    @Autowired
    public CartRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/cart/{email}")
    public ResponseEntity<?> getCart(@PathVariable String email) {
        User user = userService.findByEmail(email);
        List<Perfume> perfumeList = user.getPerfumeList();

        return new ResponseEntity<>(perfumeList, HttpStatus.OK);
    }


    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Perfume perfume, @AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());
        user.getPerfumeList().add(perfume);

        userService.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Perfume perfume, @AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());
        user.getPerfumeList().remove(perfume);

        userService.save(user);

        List<Perfume> perfumeList = user.getPerfumeList();

        return new ResponseEntity<>(perfumeList, HttpStatus.OK);
    }
}
