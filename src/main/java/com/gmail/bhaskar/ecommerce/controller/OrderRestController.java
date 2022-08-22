package com.gmail.bhaskar.ecommerce.controller;

import com.gmail.bhaskar.ecommerce.domain.Order;
import com.gmail.bhaskar.ecommerce.domain.Perfume;
import com.gmail.bhaskar.ecommerce.domain.User;
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


@RestController
@RequestMapping("/api/v1/rest")
public class OrderRestController {
    /**
     * Service object for working with user.
     */
    private final UserService userService;

    /**
     * Service object for working orders.
     */
    private final OrderService orderService;


    @Autowired
    public OrderRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());
        List<Perfume> perfumeList = user.getPerfumeList();

        return new ResponseEntity<>(perfumeList, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<?> postOrder(
            @AuthenticationPrincipal User userSession,
            @Valid @RequestBody Order validOrder,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            Order order = orderService.postOrder(validOrder, userSession);

            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
    }

    /**
     * Returns the finalize order with order index.
     * URL request {"/order/finalize"}, method GET.
     *
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/order/finalize")
    public ResponseEntity<?> finalizeOrder() {
        List<Order> orderList = orderService.findAll();
        Order orderIndex = orderList.get(orderList.size() - 1);

        return new ResponseEntity<>(orderIndex.getId(), HttpStatus.OK);
    }

    /**
     * Returns all user orders.
     * URL request {"/order/list"}, method GET.
     *
     * @param userSession requested authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/order/list")
    public ResponseEntity<?> getUserOrdersList(@AuthenticationPrincipal User userSession) {
        User user = userService.findByEmail(userSession.getEmail());
        List<Order> orders = orderService.findOrderByUser(user);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
