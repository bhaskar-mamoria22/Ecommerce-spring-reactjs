package com.gmail.bhaskar.ecommerce.controller;

import com.gmail.bhaskar.ecommerce.domain.Order;
import com.gmail.bhaskar.ecommerce.domain.Review;
import com.gmail.bhaskar.ecommerce.domain.User;
import com.gmail.bhaskar.ecommerce.dto.AuthenticationRequestDTO;
import com.gmail.bhaskar.ecommerce.service.OrderService;
import com.gmail.bhaskar.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * User controller class.
 * This controller and related pages can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @author Miroslav Khotinskiy (merikbest2015@gmail.com)
 * @version 2.0
 * @see User
 * @see Order
 * @see UserService
 * @see OrderService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class UserRestController {
    /**
     * Service object for working with users.
     */
    private final UserService userService;

    /**
     * Service object for working with orders.
     */
    private final OrderService orderService;

    /**
     * Constructor for initializing the main variables of the user controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param userService  service object for working with users.
     * @param orderService service object for working with orders.
     */
    @Autowired
    public UserRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Returns profile information for current user.
     * URL request {"/user/edit"}, method GET.
     *
     * @param userSession request authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/user/edit")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/user/edit")
    public ResponseEntity<?> updateUserInfo(
            @AuthenticationPrincipal User userSession,
            @RequestBody AuthenticationRequestDTO request
    ) {
        userService.updateProfile(userSession, request.getPassword(), request.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/user/orders")
    public ResponseEntity<?> getAllUserOrders(@AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());
        List<Order> orders = orderService.findOrderByUser(user);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PostMapping("/user/review")
    public ResponseEntity<?> addReviewToPerfume(
            @RequestParam(required = false, name = "perfumeId") Long perfumeId,
            @Valid Review review,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            userService.addReviewToPerfume(review, perfumeId);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
